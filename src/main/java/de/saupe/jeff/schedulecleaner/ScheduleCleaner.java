package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.components.CleaningAction;
import de.saupe.jeff.schedulecleaner.components.fix.EventExclusion;
import de.saupe.jeff.schedulecleaner.components.EventRange;
import de.saupe.jeff.schedulecleaner.components.fix.Fix;
import de.saupe.jeff.schedulecleaner.components.fix.TitleUpdate;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class ScheduleCleaner {
    @Setter
    private ResponseHandler responseHandler;

    private final String centuria;
    private final String semester;
    private final CleaningAction cleaningAction;

    private final List<Fix> fixes = new ArrayList<>();

    public ScheduleCleaner(String centuria, String semester, CleaningAction cleaningAction) {
        this.centuria = Utils.capitalizeOnlyFirstLetter(centuria);
        this.semester = semester;
        this.cleaningAction = cleaningAction;

        initOptionalFixes();
    }

    /**
     * This is the place to add specific fixes by yourself.
     */
    private void initOptionalFixes() {
        // Example for a title renaming
        fixes.add(new TitleUpdate("Tech.Grundlagen der Informatik 2", "TGdI"));

        // Example for an event exclusion
        fixes.add(new EventExclusion("O'Brien"));
    }

    @SneakyThrows
    public void clean() {
        List<String> lines = null;
        try {
            lines = IcsHelper.readLines(new URL(String.format(Properties.URL_ICS, centuria, semester)));
        } catch (IOException e) {
            responseHandler.onError("Failed to read the schedule. " +
                    "Check your centuria, semester and internet connection.\n" + e.getMessage());
            return;
        }

        List<EventRange> eventRanges = IcsHelper.readEventRanges(lines);

        // Apply the optional fixes
        for (EventRange eventRange : eventRanges) {
            updateTitles(lines, eventRange);
            excludeEvents(lines, eventRange);
        }

        // Remove lines of excluded events
        // To avoid line shifts caused by removing lines, it is read from bottom to top
        Collections.reverse(eventRanges);
        for (EventRange eventRange : eventRanges) {
            if (eventRange.isExcluded()) {
                lines.subList(eventRange.getStart(), eventRange.getStop() + 1).clear();
            }
        }

        // Build ICS file
        String ics = IcsHelper.build(lines);

        switch (cleaningAction) {
            case CLEAN:
                responseHandler.onDone(ics);
                break;
            case CLEAN_AND_WRITE:
                String path = Utils.getJarPath() + "\\" + centuria + "_" + semester + ".ics";
                try (PrintStream printStream = new PrintStream(path, String.valueOf(StandardCharsets.ISO_8859_1))) {
                    printStream.print(ics);
                    responseHandler.onDone(path);
                } catch (UnsupportedEncodingException e) {
                    responseHandler.onError("Writing the ICS has failed. \n" + e.getMessage());
                }
                break;
            default:
                responseHandler.onError("Unknown cleaning action.");
        }
    }

    /**
     * Update the summaries, so the titles, of specific and non-excluded events
     *
     * @param lines      All lines of the current ICS file
     * @param eventRange All event ranges
     */
    private void updateTitles(List<String> lines, EventRange eventRange) {
        if (eventRange.isExcluded())
            return;

        int descriptionIndex = Utils.findIndexOfStartsWith(lines, eventRange, "DESCRIPTION:");
        String description = descriptionIndex == -1 ? null : lines.get(descriptionIndex);

        if (description != null) {
            String title = Utils.findTitleInDescription(description);

            if (title != null) {
                // Fixes
                for (Fix fix : fixes) {
                    if (fix instanceof TitleUpdate) {
                        TitleUpdate titleUpdate = (TitleUpdate) fix;

                        if (titleUpdate.check(title)) {
                            title = titleUpdate.getNewTitle();
                        }
                    }
                }

                // Update line
                int summaryIndex = Utils.findIndexOfStartsWith(lines, eventRange, "SUMMARY:");
                lines.set(summaryIndex, "SUMMARY:" + title);
            }

        }
    }

    /**
     * Exclude specific events from the new ICS file
     *
     * @param lines      All lines of the current ICS file
     * @param eventRange All event ranges
     */
    private void excludeEvents(List<String> lines, EventRange eventRange) {
        StringBuilder line = new StringBuilder();
        for (int i = eventRange.getStart(); i <= eventRange.getStop(); i++) {
            line.append(lines.get(i));
        }

        // Fixes
        for (Fix fix : fixes) {
            if (fix instanceof EventExclusion) {
                EventExclusion eventExclusion = (EventExclusion) fix;

                boolean exclude = eventExclusion.check(line.toString());
                eventRange.setExcluded(exclude);
            }
        }
    }
}