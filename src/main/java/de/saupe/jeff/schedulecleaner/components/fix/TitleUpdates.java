package de.saupe.jeff.schedulecleaner.components.fix;

import lombok.Getter;

public class TitleUpdates extends Fix {
    @Getter
    private final String newTitle;

    public TitleUpdates(FixMethod method, String text, String newTitle) {
        super(method, text);
        this.newTitle = newTitle;
    }

}