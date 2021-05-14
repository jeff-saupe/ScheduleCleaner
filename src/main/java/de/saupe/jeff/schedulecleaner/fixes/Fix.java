package de.saupe.jeff.schedulecleaner.fixes;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;

public interface Fix {
    void apply(CalendarComponent event);
}