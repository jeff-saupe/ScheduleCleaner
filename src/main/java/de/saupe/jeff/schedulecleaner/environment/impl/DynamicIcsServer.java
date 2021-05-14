package de.saupe.jeff.schedulecleaner.environment.impl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.saupe.jeff.schedulecleaner.ResponseHandler;
import de.saupe.jeff.schedulecleaner.ScheduleCleaner;
import de.saupe.jeff.schedulecleaner.ScheduleCleaner.CleaningAction;
import de.saupe.jeff.schedulecleaner.environment.Environment;
import de.saupe.jeff.schedulecleaner.misc.Properties;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class DynamicIcsServer extends Environment implements HttpHandler {
    private final Pattern URLPattern = Pattern.compile(".*/([a-zA-Z][0-9]{2}[a-zA-Z])_([0-9])\\.ics");

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
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        Matcher matcher = URLPattern.matcher(path);
        if (!matcher.find()) {
            sendNotFoundResponse(exchange);
            return;
        }

        String centuria = matcher.group(1);
        String semester = matcher.group(2);
        if (centuria == null || semester == null) {
            sendNotFoundResponse(exchange);
            return;
        }

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
        cleaner.clean();
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

    private static void sendNotFoundResponse(HttpExchange exchange) {
        sendResponse(exchange, 404, "text", "Wrong URL format. " +
                "Example: /cleaned-schedule/<centuria>_<semester>.ics");
    }
}
