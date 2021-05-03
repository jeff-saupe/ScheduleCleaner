package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.utils.Properties;
import de.saupe.jeff.schedulecleaner.utils.Utils;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class Main {

    public Main () {
        Utils.printBanner();
        log.info("NORDAKADEMIE {} v{} has started", Properties.NAME, Properties.VERSION);
        startDialog();
    }

    private void startDialog() {
        Scanner scanner = new Scanner(System.in);

        log.info("What's your centuria?");
        String centuria = scanner.nextLine();

        log.info("In which semester are you?");
        String semester = scanner.nextLine();

        log.info("Alright. I'm starting to clean your messy schedule now..");

        Cleaner cleaner = new Cleaner(centuria, semester);
        cleaner.setResponseHandler(new ResponseHandler() {
            @Override
            public void onDone(String result) {
                log.info("Done! :)");
                log.info("The file has been saved here: " + result);
                log.info("I'm going to close myself automatically in 3 seconds..");

                try {
                    Thread.sleep(3000);
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

    public static void main(String[] args) {
        new Main();
    }
}