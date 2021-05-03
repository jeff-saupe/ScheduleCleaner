package de.saupe.jeff.schedulecleaner.utils;

import de.saupe.jeff.schedulecleaner.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;

public class Utils {

    public static void printBanner() {
        InputStream inputStream = Utils.class.getResourceAsStream("/banner.txt");
        assert inputStream != null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        bufferedReader.lines().forEach(System.out::println);
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