package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarAttribute;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.AttributeNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class UpdateTitle implements Fix {
    @Getter @Setter
    private String oldTitle;
    @Getter @Setter
    private String newTitle;

    @Override
    public void apply(CalendarComponent event) {
        CalendarAttribute titleAttribute = null;
        try {
            titleAttribute = event.getAttribute("SUMMARY");

            String currentTitle = titleAttribute.getValue();
            if (StringUtils.containsIgnoreCase(currentTitle, oldTitle)) {
                titleAttribute.setValue(newTitle);
            }
        } catch (AttributeNotFoundException e) {
            log.error(e.getMessage());
        }
    }
}