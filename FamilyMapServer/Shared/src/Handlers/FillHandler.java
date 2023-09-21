package Handlers;

import JSON.Encoder;
import Services.Fill;
import Services.results.FillResult;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                String urlPath = exchange.getRequestURI().toString();
                String[] urlParts = urlPath.split("/");
                String username = urlParts[2];


                Reader requestBody = new InputStreamReader(exchange.getRequestBody());

                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Fill fillService = new Fill();
                FillResult result;
                if(urlParts.length > 3){
                    int numGens = Integer.parseInt(urlParts[3]);
                    result = fillService.fill(numGens, username);
                    if(result.isSuccess() == false){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                }
                else{
                    result = fillService.fill(username);
                    if(result.isSuccess() == false){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                }

                String json = Encoder.serialize(result);
                resBody.write(json);
                requestBody.close();
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
