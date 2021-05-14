package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.components.CleaningAction;
import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent;
import de.saupe.jeff.schedulecleaner.components.calendar.CalendarComponent.ComponentTypes;
import de.saupe.jeff.schedulecleaner.components.calendar.IcsBuilder;
import de.saupe.jeff.schedulecleaner.components.fixes.Fix;
import de.saupe.jeff.schedulecleaner.components.fixes.impl.TitleClean;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
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
        // Title cleaning
        fixes.add(new TitleClean());

        // Example for a title renaming
        //fixes.add(new TitleUpdate("Tech.Grundlagen der Informatik 2", "Technische Grundlagen der Informatik 2"));

        // Example for an event exclusion
        //fixes.add(new EventExclusion("O'Brien"));
    }

    @SneakyThrows
    public void clean() {
        URL url = new URL(String.format(Properties.URL_ICS, centuria, semester));

        CalendarComponent calendar = null;
        try {
            calendar = IcsBuilder.toComponent(url);
        } catch (IOException e) {
            responseHandler.onError("Failed to read or parse the schedule. " +
                    "Check your centuria, semester and internet connection.\n" + e.getMessage());
            return;
        }

        List<CalendarComponent> events = calendar.getSubComponents()
                .stream()
                .filter(component -> component.getType() == ComponentTypes.VEVENT)
                .collect(Collectors.toList());

        // Apply fixes
        for (CalendarComponent event : events) {
            for (Fix fix : fixes) {
                fix.apply(event);
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