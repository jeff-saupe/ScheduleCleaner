package de.saupe.jeff.timetable;

import de.saupe.jeff.timetable.utils.Properties;
import de.saupe.jeff.timetable.utils.Utils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Scanner;

@Log4j2
public class Main {

    public static void main(String[] args) {
        try {
            Utils.printBanner();
        } catch (Exception ignored) {
        }

        log.info("{} v{} has started", Properties.NAME, Properties.VERSION);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log.info("Enter your centuria:");
            String centuria = scanner.nextLine();

            log.info("Enter your semester:");
            String semester = scanner.nextLine();

            try {
                Cleaner cleaner = new Cleaner(centuria, semester);
                cleaner.start();
                log.info("Done!");
                return;
            } catch (IOException e) {
                log.error("An error occurred: ");
                e.printStackTrace();
            }
        }
    }
}