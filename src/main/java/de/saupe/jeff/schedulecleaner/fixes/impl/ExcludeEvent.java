package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import de.saupe.jeff.schedulecleaner.fixes.FixMethod;

@Log4j2
public class ExcludeEvent extends Fix {

    public ExcludeEvent() {
        super(FixMethod.EXCLUDE, 1, 99);
    }

    @Override
    public void apply(CalendarComponent event) {
        boolean exclude = findParameters(event);
        event.setExcluded(exclude);
    }

    public boolean findParameters(CalendarComponent event) {
        String text = event.toString();
        boolean exclude = false;
        for (String parameter : getParameters()) {
            if (StringUtils.containsIgnoreCase(text, parameter))
                exclude = true;
        }
        return exclude;
    }
}