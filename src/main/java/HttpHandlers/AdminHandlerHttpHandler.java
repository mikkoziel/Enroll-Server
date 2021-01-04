package HttpHandlers;

import AdminHandler.AdminHandler;
import Tools.ServerScheduler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class AdminHandlerHttpHandler implements HttpHandler {
    String context;
    AdminHandler admin;
    ServerScheduler scheduler;

    public AdminHandlerHttpHandler(ServerScheduler scheduler){
        this.context = "/admin-handler/";
        this.admin = new AdminHandler(scheduler);
        this.scheduler = scheduler;
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
            htmlResponse = this.admin.getScheduleDetails(uri.replace("combine/", ""), id);
        } else if(uri.equals("fields")){
            htmlResponse = this.admin.getFieldsForId(id);
        } else if(uri.equals("fields-schedules")){
            htmlResponse = this.admin.getFieldsSchedules(id);
        } else if(uri.matches("field-details/[0-9]+")){
            htmlResponse = this.admin.getFieldsDetails(uri.replace("field-details/", ""));
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
            htmlResponse = this.admin.postClass(uri, msg);
        } else if(uri.matches("classes/[0-9]+")){
            htmlResponse = this.admin.postGroup(uri, msg);
        } else if(uri.equals("user-sch")){
            htmlResponse = this.admin.postUserSchedule(msg);
        } else if(uri.equals("prof")){
            htmlResponse = this.admin.postProfessor(msg);
        } else if(uri.equals("user-pref")){
            htmlResponse = this.admin.postUserPreference(msg);
        } else if(uri.equals("user")){
            htmlResponse = this.admin.postUser(msg);
        } else if(uri.equals("fos")){
            htmlResponse = this.admin.postFoS(msg, id);
        } else if(uri.equals("user-field")){
            htmlResponse = this.admin.postUserField(msg);
        } else if(uri.equals("copy-sch")){
            htmlResponse = this.admin.postCopyOfSchedule(msg, id);
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
            htmlResponse = this.admin.putClass(uri.replace("classes/", ""), msg);
        } else if(uri.matches("groups/[0-9]+")){
            htmlResponse = this.admin.putGroup(uri.replace("groups/", ""), msg);
        } else if(uri.equals("user-sch")){
            htmlResponse = this.admin.putUserSchedule(msg);
        } else if(uri.equals("user")){
            htmlResponse = this.admin.putUser(msg);
        } else if(uri.equals("enroll")){
            htmlResponse = this.admin.putEnroll(msg, id);
        } else if(uri.equals("fos")){
            htmlResponse = this.admin.putFoS(msg);
        } else if(uri.equals("user-field")){
            htmlResponse = this.admin.putUserField(msg);
        }

        return htmlResponse;
    }

    private String handleDeleteRequest(HttpExchange httpExchange) {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context,"");

        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String htmlResponse = "";
        if(uri.matches("schedules/[0-9]+")) {
            htmlResponse = this.admin.deleteSchedule(uri);
        } else if(uri.matches("classes/[0-9]+")) {
            htmlResponse = this.admin.deleteClass(uri);
        } else if(uri.matches("groups/[0-9]+")) {
            htmlResponse = this.admin.deleteGroup(uri);
        } else if(uri.matches("fos/[0-9]+")) {
            htmlResponse = this.admin.deleteFoS(uri);
        } else if(uri.matches("user-field/[0-9]+")) {
            htmlResponse = this.admin.deleteUserField(uri.replace("user-field/", ""), id);
        } else if(uri.matches("user-sch/[0-9]+")) {
            htmlResponse = this.admin.deleteUserSchedule(uri.replace("user-sch/", ""), id);
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

