package de.saupe.jeff.schedulecleaner.components.fix;

import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;

public interface Fix {
    void apply(CalendarComponent event);
}