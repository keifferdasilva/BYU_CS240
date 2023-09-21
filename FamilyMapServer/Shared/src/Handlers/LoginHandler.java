package Handlers;

import JSON.Decoder;
import JSON.Encoder;
import Services.Login;
import Services.requests.LoginRequest;
import Services.results.LoginResult;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("post")){
                Reader requestBody = new InputStreamReader(exchange.getRequestBody());



                LoginRequest request = (LoginRequest) Decoder.deserialize(requestBody, LoginRequest.class);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Login loginService = new Login();
                LoginResult result = loginService.login(request);
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
