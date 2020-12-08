import Model.Mock;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Arrays;

public class EnrollHttpHandler implements HttpHandler {
    Mock mock;
    String context;


    public EnrollHttpHandler(){
        this.mock = new Mock();
        this.context = "/enroll/admin/";
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Handler for Http requests
        String htmlResponse=null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            htmlResponse = handleGetRequest(httpExchange);
        }else if("POST".equals(httpExchange.getRequestMethod())) {
            htmlResponse = handlePostRequest(httpExchange);
        }
        System.out.println(htmlResponse);
        assert htmlResponse != null;
        handleResponse(httpExchange,htmlResponse);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        String htmlResponse=null;
        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");
//                .split("/");

        System.out.println(uri);
        if(uri.equals("schedules")){
            htmlResponse = this.mock.getSchedules();
            System.out.println(htmlResponse);
        } else if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.mock.getSchedule(uri.replace("schedules/", ""));
            System.out.println(htmlResponse);
        }
//        System.out.println(uri);
        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        // Get request Header
        Headers reqHeaders = httpExchange.getRequestHeaders();
        reqHeaders.forEach((key, value) -> System.out.println(key + ": " + value));

        // Get request body
        String mssg = this.parseMsg(httpExchange);

        String htmlResponse;
        boolean success = false;

        // Get outcome response
        if(success) {
            htmlResponse = "{ \"response\" : \"success\" }";
        } else {
            htmlResponse = "{ \" response\" : \"failure\" }";
        }
        System.out.println("Outcome: " + htmlResponse);
        return htmlResponse;
    }

    private void handleResponse(HttpExchange httpExchange, String htmlResponse)  throws  IOException {


//        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers","Origin, Content-Type");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

        // Create http response
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String parseMsg(HttpExchange httpExchange){
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



}

