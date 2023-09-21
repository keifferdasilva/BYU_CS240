package Handlers;

import JSON.Decoder;
import JSON.Encoder;
import Services.Load;
import Services.requests.LoadRequest;
import Services.results.LoadResult;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){

                Reader requestBody = new InputStreamReader(exchange.getRequestBody());
                LoadRequest request = (LoadRequest) Decoder.deserialize(requestBody, LoadRequest.class);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Load loadService = new Load();
                LoadResult result = loadService.load(request);
                if(result.isSuccess() == false){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
