package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.components.CleaningAction;
import de.saupe.jeff.schedulecleaner.components.fix.EventExclusion;
import de.saupe.jeff.schedulecleaner.components.EventRange;
import de.saupe.jeff.schedulecleaner.components.fix.TitleUpdate;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import de.saupe.jeff.schedulecleaner.components.fix.Fix.FixMethod;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
public class ScheduleCleaner {
    @Setter
    private ResponseHandler responseHandler;

    private final String centuria;
    private final String semester;
    private final CleaningAction cleaningAction;

    private final List<TitleUpdate> titleUpdates = new ArrayList<>();
    private final List<EventExclusion> eventExclusions = new ArrayList<>();

    public ScheduleCleaner(String centuria, String semester, CleaningAction cleaningAction) {
        this.centuria = centuria;
        this.semester = semester;
        this.cleaningAction = cleaningAction;

        initOptionalFixes();
    }

    /**
     * This is the place to add specific fixes by yourself.
     */
    private void initOptionalFixes() {
        // Title renaming
        titleUpdates.add(new TitleUpdate(FixMethod.CONTAINS, "Tech.Grundlagen der Informatik 2",
                "Tech. Grundlagen der Informatik 2"));

        // Event exclusions
        eventExclusions.add(new EventExclusion(FixMethod.CONTAINS, "O'Brien"));
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

        for (int i = eventRange.getStart(); i <= eventRange.getStop(); i++) {
            String line = lines.get(i);

            if (line.startsWith("SUMMARY:")) {
                String summary = line.split(",")[1];

                // Split summary
                List<String> summaryParts = Arrays.asList(summary.split(" "));

                int summaryPosition = 0;
                try {
                    summaryPosition = Utils.hasModule(summaryParts.get(1)) ? 2 : 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Build summary
                StringBuilder stringBuilder = new StringBuilder();

                for (int j = summaryPosition; j < summaryParts.size(); j++) {
                    stringBuilder.append(summaryParts.get(j).replace("\\", ""));

                    if (j < summaryParts.size() - 1) {
                        stringBuilder.append(" ");
                    }
                }

                // Update summary
                summary = stringBuilder.toString();

                // Fixes
                for (TitleUpdate titleUpdate : titleUpdates) {
                    if (titleUpdate.check(summary)) {
                        summary = titleUpdate.getNewTitle();
                    }
                }

                // Update line
                lines.set(i, "SUMMARY:" + summary);
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
        for (int i = eventRange.getStart(); i <= eventRange.getStop(); i++) {
            String line = lines.get(i);

            eventExclusions.forEach(fix -> {
                boolean exclude = fix.check(line);
                eventRange.setExcluded(exclude);
            });
        }
    }
}