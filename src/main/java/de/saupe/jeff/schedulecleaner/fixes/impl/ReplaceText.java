package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarAttribute;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReplaceText implements Fix {
    @Getter @Setter
    private String oldText;
    @Getter @Setter
    private String newText;

    @Override
    public void apply(CalendarComponent event) {
        for(CalendarAttribute attribute : event.getAttributes()) {
            String value = attribute.getValue();
            value = value.replaceAll("(?i)" + oldText, newText);
            attribute.setValue(value);
        }
    }
}