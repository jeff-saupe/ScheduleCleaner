package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.PropertyNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class AddLocation implements Fix {
    private static final Pattern roomPattern = Pattern.compile("(.*)(Raum:)(.*?)(\\\\n)");

    @Override
    public void apply(CalendarComponent event) {
        Map.Entry<String, String> descriptionProperty;
        try {
            descriptionProperty = event.getEntry("DESCRIPTION");

            String description = descriptionProperty.getValue();
            String room = findRoomInDescription(description);
            if (room != null) {
                event.getProperties().put("LOCATION", "Raum: " + room);
            }
        } catch (PropertyNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private String findRoomInDescription(String description) {
        Matcher matcher = roomPattern.matcher(description);
        if (matcher.find()) {
            return matcher.group(3).trim();
        }
        return null;
    }
}