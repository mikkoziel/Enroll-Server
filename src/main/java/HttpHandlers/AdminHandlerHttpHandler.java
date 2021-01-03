package HttpHandlers;

import AdminHandler.AdminHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class AdminHandlerHttpHandler implements HttpHandler {
    String context;
    AdminHandler admin;


    public AdminHandlerHttpHandler(){
        this.context = "/admin-handler/";
        this.admin = new AdminHandler();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
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
                htmlResponse = handleOptionsRequest();
                break;
        }
        System.out.println(htmlResponse);
        assert htmlResponse != null;
        handleResponse(httpExchange, htmlResponse);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        String htmlResponse="";
        if(uri.equals("schedules")){
            htmlResponse = this.admin.getSchedules(id);
        } else if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.admin.getSchedule(id, uri.replace("schedules/", ""));
        } else if(uri.equals("professors")){
            htmlResponse = this.admin.getProfessors();
        } else if(uri.equals("users")){
            htmlResponse = this.admin.getUsers();
        } else if(uri.matches("users/[0-9]+")){
            htmlResponse = this.admin.getUsersForSchedule(uri.replace("users/", ""));
        } else if(uri.matches("combine/[0-9]+")){
            htmlResponse = this.admin.getScheduleProfUS(uri.replace("combine/", ""), id);
        } else if(uri.equals("fields")){
            htmlResponse = this.admin.getFieldsForId(id);
        } else if(uri.equals("fields-schedules")){
            htmlResponse = this.admin.getFieldsSchedules(id);
        }
        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        String msg = this.parseMsg(httpExchange);

        String htmlResponse = "";
        if(uri.equals("schedules")){
            htmlResponse = this.admin.postSchedule(msg, id);
        } else if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.admin.postClass(uri, msg, id);
        } else if(uri.matches("classes/[0-9]+")){
            htmlResponse = this.admin.postGroup(uri, msg, id);
        } else if(uri.equals("user-sch")){
            htmlResponse = this.admin.postUserSchedule(msg);
        } else if(uri.equals("prof")){
            htmlResponse = this.admin.postProfessor(msg);
        } else if(uri.equals("user-pref")){
            htmlResponse = this.admin.postUserPreference(msg);
        } else if(uri.equals("user")){
            htmlResponse = this.admin.postUser(msg);
        }
        return htmlResponse;
    }

    private String handlePutRequest(HttpExchange httpExchange) {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        String msg = this.parseMsg(httpExchange);

        String htmlResponse = "";
        if(uri.matches("schedules/[0-9]+")){
            htmlResponse = this.admin.putSchedule(msg, id);
        } else if(uri.matches("classes/[0-9]+")){
            htmlResponse = this.admin.putClass(uri.replace("classes/", ""), msg, id);
        } else if(uri.matches("groups/[0-9]+")){
            htmlResponse = this.admin.putGroup(uri.replace("groups/", ""), msg, id);
        } else if(uri.equals("user-sch")){
            htmlResponse = this.admin.putUserSchedule(msg);
        } else if(uri.equals("user")){
            htmlResponse = this.admin.putUser(msg);
        } else if(uri.equals("enroll")){
            htmlResponse = this.admin.putEnroll(msg, id);
        }

        return htmlResponse;
    }

    private String handleDeleteRequest(HttpExchange httpExchange) {
        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        String htmlResponse = "";
        if(uri.matches("schedules/[0-9]+")) {
            htmlResponse = this.admin.deleteSchedule(uri);
        } else if(uri.matches("classes/[0-9]+")) {
            htmlResponse = this.admin.deleteClass(uri);
        } else if(uri.matches("groups/[0-9]+")) {
            htmlResponse = this.admin.deleteGroup(uri);
        }
        return htmlResponse;
    }

    private String handleOptionsRequest() {
        return "";
    }

    private void handleResponse(HttpExchange httpExchange, String htmlResponse)  throws  IOException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers","*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");

//        this.getLength(htmlResponse);
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

    private void getLength(String string){
        int length = 0;
        for(char letter: string.toCharArray()){
            length++;
        }
        System.out.println(string.length());
        System.out.println(length);
    }

}

