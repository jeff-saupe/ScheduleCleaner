package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarAttribute;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import de.saupe.jeff.schedulecleaner.fixes.FixMethod;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReplaceText extends Fix {

    public ReplaceText() {
        super(FixMethod.REPLACE, 2, 2);
    }

    @Override
    public void apply(CalendarComponent event) {
        for(CalendarAttribute attribute : event.getAttributes()) {
            String value = attribute.getValue();
            value = value.replaceAll("(?i)" + getParameters().get(0), getParameters().get(1));
            attribute.setValue(value);
        }
    }
}