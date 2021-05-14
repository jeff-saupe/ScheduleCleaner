package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.PropertyNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Log4j2
public class UpdateTitle implements Fix {
    @Getter
    private final String oldTitle;
    @Getter
    private final String newTitle;

    public UpdateTitle(String oldTitle, String newTitle) {
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
    }

    @Override
    public void apply(CalendarComponent event) {
        Map.Entry<String, String> titleProperty = null;
        try {
            titleProperty = event.getEntry("SUMMARY");

            String currentTitle = titleProperty.getValue();
            if (StringUtils.containsIgnoreCase(currentTitle, oldTitle)) {
                titleProperty.setValue(newTitle);
            }
        } catch (PropertyNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}