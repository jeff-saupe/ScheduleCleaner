package de.saupe.jeff.timetable.utils;

import de.saupe.jeff.timetable.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.CodeSource;

public class Utils {

    public static void printBanner() throws Exception {
        URL url = Main.class.getClassLoader().getResource(Properties.BANNER);
        if (url == null) {
            throw new FileNotFoundException();
        }
        File file = new File(url.getPath());
        Files.readAllLines(file.toPath()).forEach(System.out::println);
    }

    public static boolean hasModule(String summary) {
        if (summary.length() <= 5) {
            // Split into letters and numbers
            String[] letters = summary.split("(?<=\\D)(?=\\d)");

            if (letters.length == 2) {
                // Check if the first part contains letters only and the second part numbers only
                if (letters[0].matches("^[a-zA-Z]*$") && letters[1].matches("^[0-9]*$")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getJarPath() {
        CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jarFile.getParentFile().getPath();
    }

}