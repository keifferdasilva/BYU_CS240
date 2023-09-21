package Handlers;

import JSON.Encoder;
import Services.Clear;
import com.sun.net.httpserver.*;

import Services.results.ClearResult;
import java.io.*;
import java.net.*;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Clear request received");
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Clear clearService = new Clear();
                ClearResult result = clearService.clear();
                if(result.isSuccess() == false){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                String json = Encoder.serialize(result);
                resBody.write(json);
                resBody.close();
            }
        }
        catch(Exception ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
