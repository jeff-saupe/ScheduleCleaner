package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcludeEvent implements Fix {
    private final List<String> parameters = new ArrayList<>();

    public void addParameter(String parameter) {
        this.parameters.add(parameter);
    }

    public void addParameters(String... parameters) {
        this.parameters.addAll(Arrays.asList(parameters));
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