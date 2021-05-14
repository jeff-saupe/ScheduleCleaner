package de.saupe.jeff.schedulecleaner.environment;

import de.saupe.jeff.schedulecleaner.misc.Properties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Environment {
    public void start() {
        // Banner printing stays disabled until encoding has been fixed for command line
        //Utils.printBanner();

        log.info("NORDAKADEMIE {} v{} has started", Properties.NAME, Properties.VERSION);
    }
}
