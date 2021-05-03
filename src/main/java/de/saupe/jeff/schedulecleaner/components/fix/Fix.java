package de.saupe.jeff.schedulecleaner.components.fix;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Fix {

    public enum FixMethod {
        CONTAINS, EQUALS, ENDS_WITH, STARTS_WITH
    }

    private FixMethod method;
    private String text;

    public boolean check(final String line) {
        switch (method) {
            case CONTAINS:
                return line.contains(text);
            case EQUALS:
                return line.equals(text);
            case ENDS_WITH:
                return line.endsWith(text);
            case STARTS_WITH:
                return line.startsWith(text);
        }
        return true;
    }

}