package de.saupe.jeff.schedulecleaner;

import de.saupe.jeff.schedulecleaner.environment.Environment;
import de.saupe.jeff.schedulecleaner.environment.impl.Dialog;
import de.saupe.jeff.schedulecleaner.environment.impl.DynamicIcsServer;
import lombok.extern.log4j.Log4j2;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

@Log4j2
public class Main {

    public Main(boolean serverMode) {
        Environment environment;

        if (serverMode)
            environment = new DynamicIcsServer();
        else
            environment = new Dialog();

        environment.start();
    }

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("ScheduleCleaner").build();
        parser.addArgument("-s", "--servermode")
                .help("Run a server to dynamically serve ICS files.")
                .action(Arguments.storeTrue());

        try {
            Namespace ns = parser.parseArgs(args);
            new Main(ns.get("servermode"));
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }

}