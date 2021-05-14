package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.PropertyNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class CleanTitle implements Fix {
    private static final Pattern titlePattern = Pattern.compile("(.*)(Veranstaltung:)(.*?)(\\\\n)");
    private static final Pattern titleWithCodePattern = Pattern.compile("(.) (.[0-9]* )(.*)");
    private static final Pattern titleWithoutCodePattern = Pattern.compile("(.) (.*)");

    @Override
    public void apply(CalendarComponent event) {
        Map.Entry<String, String> descriptionProperty;
        try {
            descriptionProperty = event.getEntry("DESCRIPTION");

            String description = descriptionProperty.getValue();
            String cleanTitle = findTitleInDescription(description);
            if (cleanTitle != null) {
                Map.Entry<String, String> titleProperty = event.getEntry("SUMMARY");
                titleProperty.setValue(cleanTitle);
            }
        } catch (PropertyNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private String findTitleInDescription(String description) {
        Matcher matcher = titlePattern.matcher(description);
        if (matcher.find()) {
            String module = matcher.group(3).trim();

            matcher = titleWithCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(3);
            }

            matcher = titleWithoutCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(2);
            }
        }
        return null;
    }
}