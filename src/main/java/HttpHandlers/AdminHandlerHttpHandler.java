package HttpHandlers;

import AdminHandler.AdminHandler;
import Model.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class AdminHandlerHttpHandler implements HttpHandler {
    Mock mock;
    String context;
    AdminHandler admin;


    public AdminHandlerHttpHandler(){
        this.mock = new Mock();
        this.context = "/admin-handler/";
        this.admin = new AdminHandler();
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
            case "DELETE":
                htmlResponse = handleDeleteRequest(httpExchange);
                break;
            case "OPTIONS":
                htmlResponse = handleOptionsRequest(httpExchange);
                break;
        }
        System.out.println(htmlResponse);
        assert htmlResponse != null;
        handleResponse(httpExchange,htmlResponse);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        String htmlResponse=null;
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        if(uri.equals("schedules")){
//            htmlResponse = this.mock.getSchedules();
            htmlResponse = this.admin.getSchedules(id);
        } else if(uri.matches("schedules/[0-9]+")){
//            htmlResponse = this.mock.getSchedule(uri.replace("schedules/", ""));
            htmlResponse = this.admin.getSchedule(id, uri.replace("schedules/", ""));
        } else if(uri.equals("professors")){
            htmlResponse = this.admin.getProfessors();
        }
        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        // Get request Header
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        // Get request body
        String msg = this.parseMsg(httpExchange);

        String htmlResponse = null;
        if(uri.equals("schedules")){
            htmlResponse = this.admin.postSchedule(uri, msg, id);
        } else if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.admin.postClass(uri, msg, id);
        } else if(uri.matches("classes/[0-9]+")){
            htmlResponse = this.admin.postGroup(uri, msg, id);
        }
        return htmlResponse;
    }

    private String handlePutRequest(HttpExchange httpExchange) {
        return "";
    }

    private String handleDeleteRequest(HttpExchange httpExchange) {
        return "";
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

