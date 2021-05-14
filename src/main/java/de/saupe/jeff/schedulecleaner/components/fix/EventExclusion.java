package de.saupe.jeff.schedulecleaner.components.fix;

import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class EventExclusion implements Fix {
    private final List<String> parameters;

    public EventExclusion(@NonNull String... parameters) {
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