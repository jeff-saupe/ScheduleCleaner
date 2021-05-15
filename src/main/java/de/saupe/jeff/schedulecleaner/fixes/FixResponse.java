package de.saupe.jeff.schedulecleaner.fixes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FixResponse {
    TOO_MANY_PARAMETERS("Too many parameters"),
    TOO_FEW_PARAMETERS("Too few parameters"),
    OK ("OK");

    private final String name;

    public String toString() {
        return this.name;
    }
}