package de.saupe.jeff.schedulecleaner.components.fixes.impl;

import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.components.calendar.exceptions.PropertyNotFoundException;
import de.saupe.jeff.schedulecleaner.components.fixes.Fix;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Log4j2
public class TitleUpdate implements Fix {
    @Getter
    private final String oldTitle;
    @Getter
    private final String newTitle;

    public TitleUpdate(String oldTitle, String newTitle) {
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