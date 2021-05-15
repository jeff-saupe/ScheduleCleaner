package de.saupe.jeff.schedulecleaner.environment.impl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.saupe.jeff.schedulecleaner.ResponseHandler;
import de.saupe.jeff.schedulecleaner.ScheduleCleaner;
import de.saupe.jeff.schedulecleaner.ScheduleCleaner.CleaningAction;
import de.saupe.jeff.schedulecleaner.environment.Environment;
import de.saupe.jeff.schedulecleaner.fixes.Fix;
import de.saupe.jeff.schedulecleaner.fixes.FixFactory;
import de.saupe.jeff.schedulecleaner.fixes.FixResponse;
import de.saupe.jeff.schedulecleaner.misc.Properties;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class DynamicIcsServer extends Environment implements HttpHandler {
    private final Pattern URLPattern = Pattern.compile(".*/([a-zA-Z][0-9]{2}[a-zA-Z])_([0-9])\\.ics(\\?(.*)|)");
    private final Pattern fixPattern = Pattern.compile("(.*)=(.*)");

    @Override
    public void start() {
        super.start();

        String port = System.getenv("PORT"); // for Heroku
        if (port == null)
            port = "5000";

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);
            server.createContext(Properties.PATH_DYNAMIC_ICS_SERVER, this);
            server.setExecutor(null);
            server.start();
            log.info("DynamicIcsServer started on port {}", port);
        } catch (IOException e) {
            log.error("Failed to start DynamicIcsServer on port {}", port);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws UnsupportedEncodingException {
        String url = exchange.getRequestURI().toString();
        url = URLDecoder.decode(url, "UTF-8");

        // Check URL
        Matcher urlMatcher = URLPattern.matcher(url);
        if (!urlMatcher.find()) {
            sendBadURLResponse(exchange);
            return;
        }

        // Check centuria and semester
        String centuria = urlMatcher.group(1);
        String semester = urlMatcher.group(2);
        if (centuria == null || semester == null) {
            sendBadURLResponse(exchange);
            return;
        }

        // Create cleaner
        ScheduleCleaner cleaner = new ScheduleCleaner(centuria, semester, CleaningAction.CLEAN);
        cleaner.setResponseHandler(new ResponseHandler() {
            @Override
            public void onDone(String result) {
                sendResponse(exchange, 200, "text/calendar; charset=ISO-8859-1", result);
            }

            @Override
            public void onError(String message) {
                sendResponse(exchange, 404, "text", message);
            }
        });

        String tail = urlMatcher.group(4);
        try {
            // Read fixes if existent
            List<Fix> fixes = readFixes(tail);

            // Add fixes
            fixes.forEach(cleaner::addFix);

            // Start cleaner
            cleaner.clean();
        } catch (IllegalArgumentException exception) {
            sendBadRequestResponse(exchange, exception.getMessage());
        }

    }

    private List<Fix> readFixes(String url) throws IllegalArgumentException {
        List<Fix> fixes = new ArrayList<>();

        if (url != null && !url.isEmpty()) {
            if (StringUtils.countMatches(url, "?") != 0) {
                throw new IllegalArgumentException("Wrong syntax: Too many question marks.");
            } else {
                String[] parameters = url.split("&");

                // Iterate over all possible fixes
                for(String parameter : parameters) {
                    Matcher fixMatcher = fixPattern.matcher(parameter);

                    if (!fixMatcher.find()) {
                        throw new IllegalArgumentException("Wrong syntax for fix: " + parameter);
                    } else {
                        // Possible fix found
                        String fixName = fixMatcher.group(1);

                        if (fixName != null) {
                            try {
                                // Create fix
                                Fix fix = FixFactory.createFix(fixName);

                                String fixValue = fixMatcher.group(2);
                                if (fixValue != null) {
                                    String[] values = fixValue.split(";");

                                    // Insert fix' parameters and validate them
                                    FixResponse response = fix.setParameters(values);
                                    if (response == FixResponse.OK) {
                                        fixes.add(fix);
                                    } else {
                                        throw new IllegalArgumentException(response.toString() + " for " + parameter);
                                    }
                                }
                            } catch (IllegalArgumentException exception) {
                                throw new IllegalArgumentException("Fix not found: " + fixName);
                            }
                        }
                    }
                }
            }
        }

        return fixes;
    }

    private static void sendBadURLResponse(HttpExchange exchange) {
        sendNotFoundResponse(exchange, "Bad URL format. " +
                "Example: /cleaned-schedule/<centuria>_<semester>.ics");
    }

    private static void sendNotFoundResponse(HttpExchange exchange, String response) {
        sendResponse(exchange, 404, "text", response);
    }

    private static void sendBadRequestResponse(HttpExchange exchange, String response) {
        sendResponse(exchange, 400, "text", response);
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String contentType, String response) {
        try (OutputStream outputStream = exchange.getResponseBody()) {
            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            byte[] responseBytes = response.getBytes(StandardCharsets.ISO_8859_1);
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            outputStream.write(responseBytes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
