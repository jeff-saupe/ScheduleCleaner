package de.saupe.jeff.schedulecleaner.calendar;

import de.saupe.jeff.schedulecleaner.misc.Utils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IcsBuilder {
    private static final Pattern attributePattern = Pattern.compile("(.+?):(.*)");

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
            if (line.startsWith("\t")) {
                // Unfolding-Process:
                // If the line starts with a tabulator, merge it to the previous line
                int previousPosition = lines.size() - 1;

                lines.set(previousPosition, lines.get(previousPosition) + line.replace("\t", ""));
            } else {
                lines.add(line);
            }
        });

        return lines;
    }

    public static CalendarComponent toComponent(URL url) throws IOException {
        return toComponent(readLines(url));
    }

    public static CalendarComponent toComponent(List<String> lines) {
        CalendarComponent calendarComponent = null;

        while(true) {
            if (Utils.hasNext(lines)) {
                String line = Utils.next(lines);

                Matcher matcher = attributePattern.matcher(line);
                if (matcher.find()) {
                    String attributeName = matcher.group(1);
                    String attributeValue = matcher.group(2);

                    if (calendarComponent == null) {
                        // Add a new component
                        if (attributeName.equalsIgnoreCase("BEGIN")) {
                            calendarComponent = new CalendarComponent(CalendarComponent.ComponentType.valueOf(attributeValue));
                        }
                    } else {
                        if (attributeName.equalsIgnoreCase("BEGIN")) {
                            // Add a new sub-component
                            lines.add(0, line); // Re-add "BEGIN"
                            CalendarComponent subComponent = toComponent(lines);
                            calendarComponent.getComponents().add(subComponent);
                        } else if(attributeName.equalsIgnoreCase("END")) {
                            return calendarComponent;
                        } else {
                            // Add attribute to the current component
                            CalendarAttribute attribute = new CalendarAttribute(attributeName, attributeValue);
                            calendarComponent.getAttributes().add(attribute);
                        }
                    }
                }
            }
        }
    }
}
