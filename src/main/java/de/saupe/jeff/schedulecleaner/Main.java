package de.saupe.jeff.schedulecleaner;

import com.sun.net.httpserver.HttpServer;
import de.saupe.jeff.schedulecleaner.components.CleaningAction;
import de.saupe.jeff.schedulecleaner.utils.Properties;
import lombok.extern.log4j.Log4j2;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

@Log4j2
public class Main {

    public Main(boolean servermode) {
        // Banner printing will be disabled until encoding has been fixed for command line
        //Utils.printBanner();
        log.info("NORDAKADEMIE {} v{} has started", Properties.NAME, Properties.VERSION);

        if (servermode)
            startHTTPServer();
        else
            startDialog();
    }

    private void startHTTPServer() {
        String port = System.getenv("PORT"); //für Heroku
        if (port == null)
            port = "5000";
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);
            server.createContext(Properties.PATH_DynamicIcsServer, new DynamicIcsServer());
            server.setExecutor(null);
            server.start();
            log.info("DynamicIcsServer started on port {}", port);
        } catch (IOException e) {
            log.error("Failed to start DynamicIcsServer on port {}", port);
        }
    }

    private void startDialog() {
        Scanner scanner = new Scanner(System.in);

        log.info("What's your centuria?");
        String centuria = scanner.nextLine();

        log.info("In which semester are you?");
        String semester = scanner.nextLine();

        log.info("Alright. I'm starting to clean your messy schedule now..");

        Cleaner cleaner = new Cleaner(centuria, semester, CleaningAction.CLEAN);
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
        ArgumentParser parser = ArgumentParsers.newFor("ScheduleCleaner").build();
        parser.addArgument("-s", "--servermode").help("run a server to dynamically serve ics files").action(Arguments.storeTrue());
        try {
            Namespace ns = parser.parseArgs(args);
            new Main(ns.get("servermode"));
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
}