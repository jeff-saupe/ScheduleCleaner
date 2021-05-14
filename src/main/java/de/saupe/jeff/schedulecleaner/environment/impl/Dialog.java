package de.saupe.jeff.schedulecleaner.environment.impl;

import de.saupe.jeff.schedulecleaner.environment.ResponseHandler;
import de.saupe.jeff.schedulecleaner.ScheduleCleaner;
import de.saupe.jeff.schedulecleaner.misc.CleaningAction;
import de.saupe.jeff.schedulecleaner.environment.Environment;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class Dialog extends Environment {

    @Override
    public void start() {
        super.start();

        Scanner scanner = new Scanner(System.in);

        log.info("Hey! :)");
        log.info("What's your centuria?");
        String centuria = scanner.nextLine();

        log.info("In which semester (1-7) are you?");
        String semester = scanner.nextLine();

        log.info("Alright. I'm starting to clean your messy schedule now..");

        ScheduleCleaner cleaner = new ScheduleCleaner(centuria, semester, CleaningAction.CLEAN_AND_WRITE);
        cleaner.setResponseHandler(new ResponseHandler() {
            @Override
            public void onDone(String result) {
                log.info("Done!");
                log.info("The file has been saved here: " + result);
                log.info("I'm going to close myself automatically in 5 seconds..");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            }

            @Override
            public void onError(String message) {
                log.error(message);
                log.error("\nPlease restart the application and try again.");
            }
        });
        cleaner.clean();
    }
}
