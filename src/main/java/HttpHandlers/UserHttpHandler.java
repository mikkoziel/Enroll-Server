package HttpHandlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class UserHttpHandler implements HttpHandler {
    String context;

    public UserHttpHandler() {
        this.context = "/user/";
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // Handler for Http requests
        String htmlResponse = null;
        switch (httpExchange.getRequestMethod()) {
            case "GET":
                htmlResponse = handleGetRequest(httpExchange);
                break;
            case "POST":
                htmlResponse = handlePostRequest(httpExchange);
                break;
            case "PUT":
                htmlResponse = handlePutRequest();
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
        String htmlResponse = "";
        Headers reqHeaders = httpExchange.getRequestHeaders();
        int id = Integer.parseInt(reqHeaders.getFirst("id"));

        String uri = httpExchange.getRequestURI()
                .toString().replace(this.context, "");

//        if (uri.equals("schedules")) {
//            htmlResponse = this.user.getSchedules(id);
//        } else if (uri.matches("schedules/[0-9]+")) {
//            htmlResponse = this.admin.getSchedule(id, uri.replace("schedules/", ""));
//        }
//        System.out.println(uri);
        return htmlResponse;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        String htmlResponse = "";
//        Headers reqHeaders = httpExchange.getRequestHeaders();
//        int id = Integer.parseInt(reqHeaders.getFirst("id"));
//
//        String uri = httpExchange.getRequestURI()
//                .toString().replace(this.context, "");
//
//        String msg = this.parseMsg(httpExchange);

        return htmlResponse;
    }

    private String handlePutRequest() {
        return "";
    }

    private String handleOptionsRequest() {
        return "";
    }

    private void handleResponse(HttpExchange httpExchange, String htmlResponse) throws IOException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, Content-Type");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

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