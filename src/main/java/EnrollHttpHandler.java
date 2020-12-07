import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class EnrollHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // Handler for Http requests
            String requestParamValue=null;
            if("GET".equals(httpExchange.getRequestMethod())) {
                requestParamValue = handleGetRequest(httpExchange);
            }else if("POST".equals(httpExchange.getRequestMethod())) {
                requestParamValue = handlePostRequest(httpExchange);
            }
            handleResponse(httpExchange,requestParamValue);
        }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {
        // Get request Header
        Headers reqHeaders = httpExchange.getRequestHeaders();
        reqHeaders.forEach((key, value) -> System.out.println(key + ": " + value));

        // Get request body
        String message = null;
        try (InputStream in = httpExchange.getRequestBody()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder msgbuilder = new StringBuilder();
            int c;
            while ((c = br.read()) > -1) {
                msgbuilder.append((char) c);
            }
            message = msgbuilder.toString();
            System.out.println("Message: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        String htmlResponse;
        boolean success = false;

        // Get outcome response
        if(success) {
            htmlResponse = "{ \"response\" : \"success\" }";
        } else {
            htmlResponse = "{ \" response\" : \"failure\" }";
        }
        System.out.println("Outcome: " + htmlResponse);

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods","GET,POST");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

        // Create http response
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }



}
