package de.saupe.jeff.schedulecleaner.calendar.exceptions;

public class AttributeNotFoundException extends Exception {
    public AttributeNotFoundException(String property) {
        super(String.format("Attribute %s could not be found", property));
    }
}