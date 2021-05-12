package de.saupe.jeff.schedulecleaner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.saupe.jeff.schedulecleaner.components.CleaningAction;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicIcsServer implements HttpHandler {
    private final Pattern URLPattern = Pattern.compile(".*/([a-zA-Z][0-9]{2}[a-zA-Z])_([0-9])\\.ics");

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
