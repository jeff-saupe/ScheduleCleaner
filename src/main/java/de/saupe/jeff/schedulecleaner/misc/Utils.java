package de.saupe.jeff.schedulecleaner.misc;

import de.saupe.jeff.schedulecleaner.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.List;

public class Utils {
    public static void printBanner() {
        InputStream inputStream = Utils.class.getResourceAsStream(Properties.BANNER);
        assert inputStream != null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        bufferedReader.lines().forEach(System.out::println);
    }

    public static String capitalizeOnlyFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
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

    public static boolean hasNext(List<String> lines) {
        return lines.size() > 0;
    }

    public static String next(List<String> lines) {
        String line = lines.get(0);
        lines.remove(0);
        return line;
    }

}