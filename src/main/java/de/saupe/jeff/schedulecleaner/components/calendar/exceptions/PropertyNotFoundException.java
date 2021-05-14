package de.saupe.jeff.schedulecleaner.components.calendar.exceptions;

public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException(String property) {
        super(String.format("Property %s has not been found", property));
    }
}