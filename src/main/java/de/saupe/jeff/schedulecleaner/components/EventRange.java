package de.saupe.jeff.schedulecleaner.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventRange {
    private int start;
    private int stop;
    private boolean excluded = false;
}