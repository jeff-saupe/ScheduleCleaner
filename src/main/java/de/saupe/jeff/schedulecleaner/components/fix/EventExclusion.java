package de.saupe.jeff.schedulecleaner.components.fix;

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
    public boolean check(String text) {
        // ALL parameters have to be included in the text.
        for (String parameter : parameters) {
            if (!StringUtils.containsIgnoreCase(text, parameter)) return false;
        }
        return true;
    }
}