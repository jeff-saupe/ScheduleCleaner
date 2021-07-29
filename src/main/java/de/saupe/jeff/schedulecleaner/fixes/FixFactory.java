package de.saupe.jeff.schedulecleaner.fixes;

import de.saupe.jeff.schedulecleaner.fixes.impl.AddLocation;
import de.saupe.jeff.schedulecleaner.fixes.impl.CleanTitle;
import de.saupe.jeff.schedulecleaner.fixes.impl.ExcludeEvent;
import de.saupe.jeff.schedulecleaner.fixes.impl.ReplaceText;
import lombok.AllArgsConstructor;

public class FixFactory {
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
