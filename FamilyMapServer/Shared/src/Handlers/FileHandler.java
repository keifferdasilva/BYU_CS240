package Handlers;

import com.sun.net.httpserver.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try{
            if(exchange.getRequestMethod().equalsIgnoreCase("get")){

                String urlPath = exchange.getRequestURI().toString();
                if(urlPath.equals("/") || urlPath.isEmpty()){
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;
                File file = new File(filePath);
                if(file.exists()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), resBody);
                    resBody.close();
                    success = true;
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream resBody = exchange.getResponseBody();
                    File error = new File("web/HTML/404.html");
                    Files.copy(error.toPath(), resBody);
                    resBody.close();
                }

                if(!success){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        }
        catch(IOException ex){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            ex.printStackTrace();
        }
    }
}
