package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class ExcludeEvent extends Fix {

    public ExcludeEvent() {
        super("exclude", 1, 10);
    }

    @Override
    public void apply(CalendarComponent event) {
        boolean exclude = findParameters(event);
        event.setExcluded(exclude);
    }

    public boolean findParameters(CalendarComponent event) {
        String text = event.toString();
        for (String parameter : getParameters()) {
            if (!StringUtils.containsIgnoreCase(text, parameter))
                return false;
        }
        return true;
    }
}