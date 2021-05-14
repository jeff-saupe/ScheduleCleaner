package de.saupe.jeff.schedulecleaner.utils;

import de.saupe.jeff.schedulecleaner.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern titlePattern = Pattern.compile("(.*)(Veranstaltung:)(.*?)(\\\\n)");
    private static final Pattern titleWithCodePattern = Pattern.compile("(.) (.[0-9]* )(.*)");
    private static final Pattern titleWithoutCodePattern = Pattern.compile("(.) (.*)");

    public static void printBanner() {
        InputStream inputStream = Utils.class.getResourceAsStream(Properties.BANNER);
        assert inputStream != null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        bufferedReader.lines().forEach(System.out::println);
    }

    public static String findTitleInDescription(String description) {
        Matcher matcher = titlePattern.matcher(description);
        if (matcher.find()) {
            String module = matcher.group(3).trim();

            matcher = titleWithCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(3);
            }

            matcher = titleWithoutCodePattern.matcher(module);
            if (matcher.find()) {
                return matcher.group(2);
            }
        }
        return null;
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