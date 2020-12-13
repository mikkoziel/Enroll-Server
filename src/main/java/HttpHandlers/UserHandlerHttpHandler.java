package HttpHandlers;

import UserHandler.UserHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class UserHandlerHttpHandler implements HttpHandler {
    String context;
    UserHandler user;


    public UserHandlerHttpHandler(){
        this.context = "/user-handler/";
        this.user = new UserHandler();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Handler for Http requests
        String htmlResponse=null;
        switch(httpExchange.getRequestMethod()){
            case "GET":
                htmlResponse = handleGetRequest(httpExchange);
                break;
            case "POST":
                htmlResponse = handlePostRequest(httpExchange);
                break;
            case "OPTIONS":
                htmlResponse = handleOptionsRequest(httpExchange);
                break;
        }
//        if("GET".equals(httpExchange.getRequestMethod())) {
//            htmlResponse = handleGetRequest(httpExchange);
//        }else if("POST".equals(httpExchange.getRequestMethod())) {
//            htmlResponse = handlePostRequest(httpExchange);
//        }else if("OPTIONS")
        System.out.println(htmlResponse);
        assert htmlResponse != null;
        handleResponse(httpExchange, htmlResponse);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        String htmlResponse="";

        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));
//        reqHeaders.forEach((key, value) -> System.out.println(key + ": " + value));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

//        System.out.println(uri);
        if(uri.equals("schedules")){
//            htmlResponse = this.mock.getSchedules();
//            htmlResponse = this.admin.getSchedules(id);
//            System.out.println(htmlResponse);
        } else if(uri.matches("schedules/[0-9]+")){
//            htmlResponse = this.mock.getSchedule(uri.replace("schedules/", ""));
//            htmlResponse = this.admin.getSchedule(id, uri.replace("schedules/", ""));
//            System.out.println(htmlResponse);
        }
//        System.out.println(uri);
        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        String htmlResponse="";
        // Get request Header
        Headers reqHeaders = httpExchange.getRequestHeaders();
        reqHeaders.forEach((key, value) -> System.out.println(key + ": " + value));

        // Get request body
        String msg = this.parseMsg(httpExchange);


        return htmlResponse;
    }

    private String handleOptionsRequest(HttpExchange httpExchange) {
        return "";
    }

    private void handleResponse(HttpExchange httpExchange, String htmlResponse)  throws  IOException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers","Origin, Content-Type");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

        // Create http response
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        OutputStream outputStream = httpExchange.getResponseBody();

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private String parseMsg(HttpExchange httpExchange) {
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
