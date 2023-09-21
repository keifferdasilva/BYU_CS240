package Handlers;

import JSON.Decoder;
import JSON.Encoder;
import Services.Register;
import Services.requests.RegisterRequest;
import Services.results.RegisterResult;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                Reader requestBody = new InputStreamReader(exchange.getRequestBody());

                RegisterRequest req = (RegisterRequest) Decoder.deserialize(requestBody, RegisterRequest.class);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Register registerService = new Register();
                RegisterResult result = registerService.register(req);
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
