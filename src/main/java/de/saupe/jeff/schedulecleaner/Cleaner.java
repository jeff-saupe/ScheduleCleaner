package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.components.CleaningAction;
import de.saupe.jeff.schedulecleaner.components.fix.EventFix;
import de.saupe.jeff.schedulecleaner.components.EventRange;
import de.saupe.jeff.schedulecleaner.components.fix.SummaryFix;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import de.saupe.jeff.schedulecleaner.components.fix.Fix.FixMethod;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
public class Cleaner {
    @Setter
    private ResponseHandler responseHandler;

    private final String centuria;
    private final String semester;
    private final CleaningAction cleaningAction;

    private final List<SummaryFix> summaryFixes = new ArrayList<>();
    private final List<EventFix> eventFixes = new ArrayList<>();

    public Cleaner(String centuria, String semester, CleaningAction cleaningAction) {
        this.centuria = centuria;
        this.semester = semester;
        this.cleaningAction = cleaningAction;

        // Summary/title fixes
        summaryFixes.add(new SummaryFix(FixMethod.CONTAINS, "Tech.Grundlagen der Informatik 2",
                "Tech. Grundlagen der Informatik 2"));

        // Event Fixes
//        eventFixes.add(new EventFix(FixMethod.EQUALS, "DESCRIPTION:Studiengruppe: A19a\\, A19c\\, " +
//                "I19a\\nVeranstaltung: K I169 Allgemeine Betriebswirtschaftslehre\\nDozent: -\\nRaum:" +
//                " -\\nUhrzeit-Dauer: 9:15 - 10:45 Uhr (1:30 UE)\\nAnmerkung: -"));
//        eventFixes.add(new EventFix(FixMethod.CONTAINS, "O'Brien"));
    }

    public void clean()  {
        List<String>  lines = readLines();
        assert lines != null;

        List<EventRange> eventRanges = findEventRanges(lines);

        // Fixes
        for (EventRange eventRange : eventRanges) {
            // Update summary and title
            updateSummaries(lines, eventRange);

            // Exclude specific modules
            excludeEvents(lines, eventRange);
        }

        // Remove lines of excluded events
        Collections.reverse(eventRanges);
        eventRanges.stream().filter(EventRange::isExcluded).forEach(range -> {
            for (int i = range.getStop(); i >= range.getStart(); i--) {
                lines.remove(i);
            }
        });

        // Construct ICS file
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            stringBuilder.append(lines.get(i));

            // Line break
            if (i < lines.size() - 1)
                stringBuilder.append("\n");
        }

        switch (cleaningAction){
            case CLEAN:
                responseHandler.onDone(stringBuilder.toString());
                break;
            case CLEAN_AND_WRITE:
                String path = Utils.getJarPath() + "\\" + centuria + "_" + semester + ".ics";
                try (PrintStream printStream = new PrintStream(path, String.valueOf(StandardCharsets.ISO_8859_1))) {
                    printStream.print(stringBuilder);
                    responseHandler.onDone(path);
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    responseHandler.onError("Building the ICS has failed. \n" + e.getMessage());
                }
                break;
            default:
                responseHandler.onError("Unknown cleaning action");
        }
    }

    /**
     * Retrieve the ICS file over the internet and return every line in a list
     *
     * @return List with all lines of the ICS file
     */
    private List<String> readLines() {
        final BufferedReader bufferedReader;
        try {
            InputStream inputStream = new URL(String.format(Properties.URL_ICS, centuria, semester)).openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        } catch (Exception e) {
            responseHandler.onError("Failed to retrieve the schedule. " +
                    "Check your centuria, semester and internet connection.\n" + e.getMessage());
            return null;
        }

        List<String> lines = new ArrayList<>();
        bufferedReader.lines().forEach(line -> {
            // If the line starts with a tabulator, merge it to the previous line
            if (line.startsWith("\t")) {
                int previousPosition = lines.size() - 1;

                lines.set(previousPosition, lines.get(previousPosition) + line.replace("\t", ""));
            } else {
                lines.add(line);
            }
        });

        return lines;
    }

    /**
     * Find all events in the ICS file and note down their range
     *
     * @param lines Lines of an ICS file
     * @return List of EventRange
     */
    private List<EventRange> findEventRanges(List<String> lines) {
        List<EventRange> eventRanges = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String row = lines.get(i);

            if (row.startsWith("BEGIN:VEVENT")) {
                EventRange eventRange = new EventRange();
                eventRange.setStart(i);
                eventRanges.add(eventRange);
            } else if (row.startsWith("END:VEVENT")) {
                EventRange eventRange = eventRanges.get(eventRanges.size() - 1);
                eventRange.setStop(i);
            }
        }

        return eventRanges;
    }

    /**
     * Updates the summaries of specific and non-excluded events
     *
     * @param lines All lines of the current ICS file
     * @param eventRange All event ranges
     */
    private void updateSummaries(List<String> lines, EventRange eventRange) {
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

                // Fix
                for (SummaryFix fix : summaryFixes) {
                    if(fix.check(summary)) {
                        summary = fix.getReplacement();
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
     * @param lines All lines of the current ICS file
     * @param eventRange All event ranges
     */
    private void excludeEvents(List<String> lines, EventRange eventRange) {
        for (int i = eventRange.getStart(); i <= eventRange.getStop(); i++) {
            String line = lines.get(i);

            eventFixes.forEach(fix -> {
                boolean exclude = fix.check(line);
                eventRange.setExcluded(exclude);
            });
        }
    }
}