package de.saupe.jeff.schedulecleaner.fixes.impl;

import de.saupe.jeff.schedulecleaner.calendar.CalendarAttribute;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.exceptions.AttributeNotFoundException;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import de.saupe.jeff.schedulecleaner.fixes.FixMethod;
import lombok.extern.log4j.Log4j2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class CleanTitle extends Fix {
    private static final Pattern titlePattern = Pattern.compile("(.*)(Veranstaltung:)(.*?)(\\\\n)");
    private static final Pattern titleWithCodePattern = Pattern.compile("(.) (.[0-9]{2,3})(.*)");
    private static final Pattern titleWithoutCodePattern = Pattern.compile("(.) (.*)");

    public CleanTitle() {
        super(FixMethod.CLEAN, 0, 0);
    }

    @Override
    public void apply(CalendarComponent event) {
        CalendarAttribute descriptionAttribute;
        try {
            descriptionAttribute = event.getAttribute("DESCRIPTION");

            String description = descriptionAttribute.getValue();
            String cleanTitle = findTitleInDescription(description);
            if (cleanTitle != null) {
                CalendarAttribute titleAttribute = event.getAttribute("SUMMARY");
                titleAttribute.setValue(cleanTitle);
            }
        } catch (AttributeNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private String findTitleInDescription(String description) {
        Matcher matcher = titlePattern.matcher(description);
        if (matcher.find()) {
            String module = matcher.group(3).trim();

            matcher = titleWithCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(3).trim();
            }

            matcher = titleWithoutCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(2).trim();
            }
        }
        return null;
    }
}