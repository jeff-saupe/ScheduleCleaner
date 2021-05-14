package de.saupe.jeff.schedulecleaner.components.fixes.impl;

import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.components.calendar.exceptions.PropertyNotFoundException;
import de.saupe.jeff.schedulecleaner.components.fixes.Fix;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class TitleClean implements Fix {

    @Override
    public void apply(CalendarComponent event) {
        Map.Entry<String, String> descriptionProperty;
        try {
            descriptionProperty = event.getEntry("DESCRIPTION");

            String description = descriptionProperty.getValue();
            String cleanTitle = Utils.findTitleInDescription(description);
            if (cleanTitle != null) {
                Map.Entry<String, String> titleProperty = event.getEntry("SUMMARY");
                titleProperty.setValue(cleanTitle);
            }
        } catch (PropertyNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}