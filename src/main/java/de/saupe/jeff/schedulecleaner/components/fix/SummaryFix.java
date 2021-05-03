package de.saupe.jeff.schedulecleaner.components.fix;

import lombok.Getter;

public class SummaryFix extends Fix {
    @Getter
    private String replacement;

    public SummaryFix(FixMethod method, String text, String replacement) {
        super(method, text);
        this.replacement = replacement;
    }

}
