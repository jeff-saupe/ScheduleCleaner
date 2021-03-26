package de.saupe.jeff.timetable.utils;

import de.saupe.jeff.timetable.Main;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class Utils {

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