package de.saupe.jeff.schedulecleaner.components.fix;

import lombok.Getter;

public class TitleUpdate implements Fix {
    @Getter
    private final String oldTitle;
    @Getter
    private final String newTitle;

    public TitleUpdate(String oldTitle, String newTitle) {
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
    }

    @Override
    public boolean check(String text) {
        return text.contains(oldTitle);
    }
}