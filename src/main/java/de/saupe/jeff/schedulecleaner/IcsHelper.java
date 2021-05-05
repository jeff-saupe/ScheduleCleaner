package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.components.EventRange;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IcsHelper {
    /**
     * Retrieve the ICS file over the internet and return every line in a list
     *
     * @return List with all lines of the ICS file
     */
    public static List<String> readLines(URL url) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));

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
     * @return List of EventRange
     */
    public static List<EventRange> readEventRanges(List<String> lines) {
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

    public static String build(List<String> lines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            stringBuilder.append(lines.get(i));

            // Line break
            if (i < lines.size() - 1) stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
