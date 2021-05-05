package de.saupe.jeff.schedulecleaner.components.fix;

public abstract class Fix {

    public enum FixMethod {
        CONTAINS, EQUALS, ENDS_WITH, STARTS_WITH
    }

    private final FixMethod method;
    private final String text;

    public Fix(FixMethod method, String text) {
        this.method = method;
        this.text = text.trim();
    }

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