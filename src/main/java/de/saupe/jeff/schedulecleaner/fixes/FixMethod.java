package de.saupe.jeff.schedulecleaner.fixes;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum FixMethod {
    CLEAN("clean"),
    LOCATION("location"),
    EXCLUDE("exclude"),
    REPLACE("replace");

    private final String name;

    public static FixMethod fromString(String name) {
        for (FixMethod method : FixMethod.values()) {
            if (method.name.equalsIgnoreCase(name)) {
                return method;
            }
        }
        throw new IllegalArgumentException(String.format("FixMethod '%s' does not exist.", name));
    }
}
