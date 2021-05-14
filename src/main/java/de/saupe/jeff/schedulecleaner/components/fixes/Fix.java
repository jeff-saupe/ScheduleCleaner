package de.saupe.jeff.schedulecleaner.components.fixes;

import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;

public interface Fix {
    void apply(CalendarComponent event);
}