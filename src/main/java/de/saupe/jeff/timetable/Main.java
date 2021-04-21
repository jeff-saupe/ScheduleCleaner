package de.saupe.jeff.timetable;

import de.saupe.jeff.timetable.utils.Logger;
import de.saupe.jeff.timetable.utils.Properties;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Logger.info(String.format(Properties.NAME + " v%s started", Properties.VERSION));

        Scanner scanner = new Scanner(System.in);
        while(true) {
            Logger.info("Enter your centuria:");
            Properties.CENTURIA = scanner.nextLine();

            Logger.info("Enter your semester:");
            Properties.SEMESTER = scanner.nextLine();

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