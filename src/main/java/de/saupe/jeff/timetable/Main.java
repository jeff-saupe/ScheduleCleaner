package de.saupe.jeff.timetable;

import de.saupe.jeff.timetable.utils.Logger;
import de.saupe.jeff.timetable.utils.Settings;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final static String NAME = "TimetableCleaner";
    private final static String VERSION = "1.0";

    public static void main(String[] args) {
        Logger.info(String.format(NAME + " v%s started", VERSION));

        Scanner scanner = new Scanner(System.in);
        while(true) {
            Logger.info("Enter your centuria:");
            Settings.centuria = scanner.nextLine();

            Logger.info("Enter your semester:");
            Settings.semester = scanner.nextLine();

            try {
                Cleaner cleaner = new Cleaner();
                cleaner.start();
                Logger.info("Done!");
                return;
            } catch (IOException e) {
                Logger.error("An error occurred: ");
                e.printStackTrace();
            }
        }
    }
}