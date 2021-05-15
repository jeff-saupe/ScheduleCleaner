package de.saupe.jeff.schedulecleaner.fixes;

import de.saupe.jeff.schedulecleaner.fixes.impl.AddLocation;
import de.saupe.jeff.schedulecleaner.fixes.impl.CleanTitle;
import de.saupe.jeff.schedulecleaner.fixes.impl.ExcludeEvent;
import de.saupe.jeff.schedulecleaner.fixes.impl.ReplaceText;
import lombok.AllArgsConstructor;

public class FixFactory {
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
            throw new IllegalArgumentException(String.format("FixMethod %s does not exist.", name));
        }
    }

    public static Fix createFix(String method) {
        return createFix(FixMethod.fromString(method));
    }

    public static Fix createFix(FixMethod method) {
        switch (method){
            case CLEAN: return new CleanTitle();
            case LOCATION: return new AddLocation();
            case REPLACE: return new ReplaceText();
            case EXCLUDE: return new ExcludeEvent();
            default: throw new IllegalArgumentException(String.format("FixMethod %s not included in FixFactory", method.name()));
        }
    }
}
