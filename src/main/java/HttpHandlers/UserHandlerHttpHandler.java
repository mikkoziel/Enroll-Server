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
            case "PUT":
                htmlResponse = handlePutRequest(httpExchange);
                break;
            case "OPTIONS":
                htmlResponse = handleOptionsRequest();
                break;
        }
        System.out.println(htmlResponse);
        assert htmlResponse != null;
        handleResponse(httpExchange, htmlResponse);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        String htmlResponse="";

        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        if(uri.equals("schedules")){
            htmlResponse = this.user.getSchedules(id);
        } else if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.user.getSchedule(id, uri.replace("schedules/", ""));
        } else if(uri.equals("professors")){
            htmlResponse = this.user.getProfessors();
        } else if(uri.matches("user-pref/[0-9]+")){
            htmlResponse = this.user.getUPForUser(uri.replace("user-pref/", ""));
        } else if(uri.matches("combine/[0-9]+")){
            htmlResponse = this.user.getScheduleProfUP(uri.replace("combine/", ""), id);
        }

        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        String htmlResponse="";
        // Get request Header
        Headers reqHeaders = httpExchange.getRequestHeaders();
        reqHeaders.forEach((key, value) -> System.out.println(key + ": " + value));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        // Get request body
        String msg = this.parseMsg(httpExchange);

        if(uri.equals("user-pref")){
            htmlResponse = this.user.postUserPreference(msg);
        }

        return htmlResponse;
    }

    private String handlePutRequest(HttpExchange httpExchange) {
        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        String msg = this.parseMsg(httpExchange);

        String htmlResponse = "";
        if(uri.equals("user-sch")){
            htmlResponse = this.user.putUserPreference(msg);
        }

        return htmlResponse;
    }

    private String handleOptionsRequest() {
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
            StringBuilder msgBuilder = new StringBuilder();
            int c;
            while ((c = br.read()) > -1) {
                msgBuilder.append((char) c);
            }
            message = msgBuilder.toString();
            System.out.println("Message: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
