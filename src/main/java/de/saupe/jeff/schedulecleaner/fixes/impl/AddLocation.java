package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarAttribute;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.AttributeNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.extern.log4j.Log4j2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class AddLocation extends Fix {
    private static final Pattern roomPattern = Pattern.compile("(.*)(Raum:)(.*?)(\\\\n)");

    public AddLocation() {
        super("location", 0, 0);
    }

    @Override
    public void apply(CalendarComponent event) {
        CalendarAttribute descriptionAttribute;
        try {
            descriptionAttribute = event.getAttribute("DESCRIPTION");

            String description = descriptionAttribute.getValue();
            String room = findRoomInDescription(description);
            if (room != null) {
                CalendarAttribute locationAttribute;
                try {
                    locationAttribute = event.getAttribute("LOCATION");
                } catch (AttributeNotFoundException e) {
                    locationAttribute = new CalendarAttribute("LOCATION", "");
                }
                locationAttribute.setValue("Raum: " + room);
            }
        } catch (AttributeNotFoundException e) {
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