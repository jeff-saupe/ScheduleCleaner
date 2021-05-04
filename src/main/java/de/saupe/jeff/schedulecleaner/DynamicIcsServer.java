package de.saupe.jeff.schedulecleaner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.saupe.jeff.schedulecleaner.components.CleaningAction;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicIcsServer implements HttpHandler{
    private final Pattern URLPattern = Pattern.compile(".*/([A-Z][0-9]{2}[a-z])-([0-9])\\.ics");

    private static void sendResponse(HttpExchange t, int statusCode, String contentType, String response){
        try(OutputStream os = t.getResponseBody()){
            t.getResponseHeaders().add("Content-Type", contentType);
            byte[] responseBytes = response.getBytes(StandardCharsets.ISO_8859_1);
            t.sendResponseHeaders(statusCode, responseBytes.length);
            os.write(responseBytes);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void send404Response(HttpExchange t){
        sendResponse(t, 404, "text", "Wrong URL format. Example: /cleanded-schedule/<centuria>-<semester>.ics");
    }

    @Override
    public void handle(HttpExchange t) {
        String path = t.getRequestURI().getPath();
        Matcher matcher = URLPattern.matcher(path);
        if (!matcher.find()) {
            send404Response(t);
            return;
        }

        String centuria = matcher.group(1);
        String semester = matcher.group(2);
        if(centuria == null || semester == null) {
            send404Response(t);
            return;
        }

        Cleaner cleaner = new Cleaner(centuria, semester, CleaningAction.CLEAN);
        cleaner.setResponseHandler(new ResponseHandler() {
            @Override
            public void onDone(String result) {
                sendResponse(t, 200, "text/calendar", result);
            }

            @Override
            public void onError(String message) {
                sendResponse(t, 404, "text", message);
            }
        });
        cleaner.clean();
    }
}
