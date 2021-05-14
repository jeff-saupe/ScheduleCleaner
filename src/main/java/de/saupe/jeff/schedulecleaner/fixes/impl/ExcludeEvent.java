package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class ExcludeEvent implements Fix {
    private final List<String> parameters;

    public ExcludeEvent(@NonNull String... parameters) {
        this.parameters = Arrays.asList(parameters);
    }

    @Override
    public void apply(CalendarComponent event) {
        boolean exclude = check(event);
        event.setExcluded(exclude);
    }

    public boolean check(CalendarComponent event) {
        String text = event.toString();
        for (String parameter : parameters) {
            if (!StringUtils.containsIgnoreCase(text, parameter)) return false;
        }
        return true;
    }
}