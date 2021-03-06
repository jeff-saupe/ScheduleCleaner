package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.calendar.CalendarComponent.ComponentType;
import de.saupe.jeff.schedulecleaner.calendar.CalendarBuilder;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import de.saupe.jeff.schedulecleaner.fixes.FixFactory;
import de.saupe.jeff.schedulecleaner.fixes.FixMethod;
import de.saupe.jeff.schedulecleaner.misc.Properties;
import de.saupe.jeff.schedulecleaner.misc.Utils;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ScheduleCleaner {
    public enum CleaningAction {
        CLEAN,
        CLEAN_AND_WRITE
    }

    @Setter
    private ResponseHandler responseHandler;

    private final String centuria;
    private final String semester;
    private final CleaningAction cleaningAction;

    private final List<Fix> fixes = new ArrayList<>();

    public ScheduleCleaner(String centuria, String semester, CleaningAction cleaningAction) {
        this.centuria = Utils.capitalizeOnlyFirstLetter(centuria);
        this.semester = semester;
        this.cleaningAction = cleaningAction;

        initFixes();
    }

    /**
     * This is the place to add specific fixes by yourself.
     */
    private void initFixes() {
        // [Default] Title cleaning
        Fix clean = FixFactory.createFix(FixMethod.TITLE);
        addFix(clean);

        // [Example] Add the room as a location
//        Fix location = FixFactory.createFix(FixMethod.LOCATION);
//        addFix(location);

        // [Example] Replaces a text with another text
//        Fix replaceText = FixFactory.createFix(FixMethod.REPLACE);
//        replaceText.setParameters(
//                "Tech.Grundlagen der Informatik 2",
//                "Technische Grundlagen der Informatik 2");
//        addFix(replaceText);

        // [Example] Event exclusion
//        Fix excludeEvent = FixFactory.createFix(FixMethod.EXCLUDE);
//        excludeEvent.setParameters(
//                "O'Brien",
//                "Englisch");
//        addFix(excludeEvent);
    }

    public void addFix(Fix fix) throws IllegalArgumentException {
        if (fix.check()) {
            if(fix.getMethod() == FixMethod.TITLE) {
                // Override default CleanTitle of initFixes()
                fixes.removeIf(f -> f.getMethod() == FixMethod.TITLE);
            }
            fixes.add(fix);
        }
    }

    @SneakyThrows
    public void clean() {
        URL url = new URL(String.format(Properties.URL_ICS, centuria, semester));

        CalendarComponent calendar = null;
        try {
            calendar = CalendarBuilder.getCalendar(url);
        } catch (IOException e) {
            responseHandler.onError("Failed to read or parse the schedule. " +
                    "Check your centuria, semester and internet connection.\n" + e.getMessage());
            return;
        }

        List<CalendarComponent> events = calendar.getComponents()
                .stream()
                .filter(component -> component.getType() == ComponentType.VEVENT)
                .collect(Collectors.toList());

        // Apply fixes
        for (CalendarComponent event : events) {
            for (Fix fix : fixes) {
                if (!event.isExcluded()) {
                    fix.apply(event);
                }
            }
        }

        // Build ICS file
        String ics = calendar.toString();

        switch (cleaningAction) {
            case CLEAN:
                responseHandler.onDone(ics);
                break;
            case CLEAN_AND_WRITE:
                String path = Utils.getJarPath() + "\\" + centuria + "_" + semester + ".ics";
                try (PrintStream printStream = new PrintStream(path, String.valueOf(StandardCharsets.ISO_8859_1))) {
                    printStream.print(ics);
                    responseHandler.onDone(path);
                } catch (UnsupportedEncodingException e) {
                    responseHandler.onError("Writing the ICS has failed. \n" + e.getMessage());
                }
                break;
            default:
                responseHandler.onError("Unknown cleaning action.");
        }
    }
}