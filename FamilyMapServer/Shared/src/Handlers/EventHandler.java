package Handlers;

import Services.results.EventsResult;
import com.sun.net.httpserver.*;
import Services.EventService;
import Services.results.EventResult;
import JSON.Encoder;

import java.io.*;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){
                String urlPath = exchange.getRequestURI().toString();
                String[] urlParts = urlPath.split("/");




                Headers reqHeaders = exchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")) {
                    String authtoken = reqHeaders.getFirst("Authorization");
                    Reader requestBody = new InputStreamReader(exchange.getRequestBody());

                    Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

                    EventService eventService = new EventService(authtoken);

                    String json;
                    if (urlParts.length > 2) {
                        String eventID = urlParts[2];
                        EventResult result = eventService.getEvent(eventID);
                        if(result.isSuccess() == false){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        json = Encoder.serialize(result);
                    } else {
                        EventsResult result = eventService.getAllEvents();
                        json = Encoder.serialize(result);
                        if(result.isSuccess() == false){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                    }

                    resBody.write(json);
                    requestBody.close();
                    resBody.close();
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }


            }
        }
        catch(Exception ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
