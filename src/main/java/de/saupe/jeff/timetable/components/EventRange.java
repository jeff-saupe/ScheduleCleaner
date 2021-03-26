package de.saupe.jeff.timetable.components;

public class EventRange {
    public int start;
    public int stop;
    public boolean remove = false;

    public EventRange() {
    }

    public EventRange(int start, int end) {
        this.start = start;
        this.stop = end;
    }
}