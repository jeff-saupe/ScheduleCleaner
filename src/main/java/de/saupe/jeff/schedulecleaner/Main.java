package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Scanner;

@Log4j2
public class Main {

    public Main () {
        Utils.printBanner();
        log.info("{} v{} has started", Properties.NAME, Properties.VERSION);

        startDialog();
    }

    private void startDialog() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            log.info("What's your centuria?");
            String centuria = scanner.nextLine();

            log.info("In which semester are you?");
            String semester = scanner.nextLine();

            try {
                log.info("Alright. I'm cleaning your messy schedule now..");
                Cleaner cleaner = new Cleaner(centuria, semester);
                cleaner.start();
                log.info("Done! :)");
                return;
            } catch (IOException e) {
                log.error("An error occurred: \n" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}