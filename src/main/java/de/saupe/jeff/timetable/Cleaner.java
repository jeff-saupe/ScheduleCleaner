package de.saupe.jeff.timetable;

import de.saupe.jeff.timetable.components.EventRange;
import de.saupe.jeff.timetable.utils.Properties;
import de.saupe.jeff.timetable.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class Cleaner {
    private String centuria;
    private String semester;

    public void start() throws IOException {
        try {
            InputStream inputStream = new URL(String.format(Properties.URL_ICS, centuria, semester)).openStream();
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);
            for (Object object : calendar.getComponents(Component.VEVENT)) {
                Component component = (Component) object;
                System.out.println(component.toString());
            }
        } catch (Exception e) {
            log.error("Failed to retrieve the plan");
        }


//        List<String> lines = getLines();
//        List<EventRange> eventRanges = getEventRanges(lines);
//
//        // Corrections
//        for (EventRange eventRange : eventRanges) {
//            // Update summary and title
//            updateSummary(lines, eventRange);
//
//            // Remove specific courses
//            removeEvents(lines, eventRange);
//        }
//
//        // Remove ignored event ranges from list
//        Collections.reverse(eventRanges);
//        eventRanges.stream().filter(range -> range.remove).forEach(range -> {
//            for (int i = range.stop; i >= range.start; i--) {
//                lines.remove(i);
//            }
//        });
//
//        // Rebuild ICS file by concatenating every line
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < lines.size(); i++) {
//            stringBuilder.append(lines.get(i));
//
//            if (i < lines.size() - 1)
//                stringBuilder.append("\n");
//        }
//
//        // Build ICS file
//        try (PrintStream printStream = new PrintStream(Utils.getJarPath() + "\\" + centuria + "_" + semester + ".ics", String.valueOf(StandardCharsets.ISO_8859_1))) {
//            printStream.print(stringBuilder.toString());
//        }
    }

    /**
     * Retrieve the ICS file from the internet and reads all lines of it one by one
     *
     * @return List with all lines of the ICS FILE
     * @throws IOException
     */
    private List<String> getLines() throws IOException {
        InputStream inputStream;
        BufferedReader bufferedReader;

        try {
            inputStream = new URL(String.format(Properties.URL_ICS, centuria, semester)).openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        } catch (Exception e) {
            log.error("Failed to retrieve the plan");
            return null;
        }

        // Paste every line into a list one by one
        List<String> lines = new ArrayList<>();

        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            // Merge tabulator line to the previous line
            if (currentLine.startsWith("\t")) {
                int previousPosition = lines.size() - 1;

                // Update previous line
                lines.set(previousPosition, lines.get(previousPosition) + currentLine.replace("\t", ""));
            } else {
                lines.add(currentLine);
            }
        }

        return lines;
    }

    /**
     * Find all events in the ICS file as ranges
     *
     * @param lines
     * @return a list of event ranges
     */
    private List<EventRange> getEventRanges(List<String> lines) {
        List<EventRange> eventRanges = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String row = lines.get(i);

            if (row.equals("BEGIN:VEVENT")) {
                eventRanges.add(new EventRange());

                EventRange eventRange = eventRanges.get(eventRanges.size() - 1);
                eventRange.start = i;
            } else if (row.equals("END:VEVENT")) {
                EventRange eventRange = eventRanges.get(eventRanges.size() - 1);
                eventRange.stop = i;
            }
        }

        return eventRanges;
    }

    /**
     * Updates the summaries of specific events
     *
     * @param lines
     * @param eventRange
     */
    private void updateSummary(List<String> lines, EventRange eventRange) {
        if (eventRange.remove)
            return;

        for (int i = eventRange.start; i <= eventRange.stop; i++) {
            String line = lines.get(i);

            if (line.startsWith("SUMMARY:")) {
                String summary = line.split("\\,")[1]; // Divide by commas

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
                if (summary.contains("Tech.Grundlagen der Informatik 2")) {
                    summary = "Tech. Grundlagen der Informatik 2";
                }

                // Update line
                lines.set(i, "SUMMARY:" + summary);
            }
        }
    }

    /**
     * Removes specific events
     *
     * @param lines
     * @param eventRange
     */
    private void removeEvents(List<String> lines, EventRange eventRange) {
        for (int i = eventRange.start; i <= eventRange.stop; i++) {
            String row = lines.get(i);

            // Allgemeine Betriebswirtschaftslehre
            if (row.equals("DESCRIPTION:Studiengruppe: A19a\\, A19c\\, I19a\\nVeranstaltung: K I169 Allgemeine Betriebswirtschaftslehre\\nDozent: -\\nRaum: -\\nUhrzeit-Dauer: 9:15 - 10:45 Uhr (1:30 UE)\\nAnmerkung: -"))
                eventRange.remove = true;

            // Englisch bei O'Brien
            if (row.contains("O'Brien"))
                eventRange.remove = true;
        }
    }
}
