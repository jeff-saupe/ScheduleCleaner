package de.saupe.jeff.schedulecleaner.components.fix;

import lombok.Getter;

public class TitleUpdate extends Fix {
    @Getter
    private final String newTitle;

    public TitleUpdate(FixMethod method, String text, String newTitle) {
        super(method, text);
        this.newTitle = newTitle;
    }

}