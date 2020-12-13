
import HttpHandlers.AdminHandlerHttpHandler;
import HttpHandlers.UserHandlerHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerMain {
    public static void main(String[] args){
        System.out.println("Hello World");
        createServer();
    }

    private static void createServer(){
        int port = 3999;
        try{
            // Create HttpServer
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

            server.createContext("/admin-handler", new AdminHandlerHttpHandler());
            server.createContext("/user-handler", new UserHandlerHttpHandler());
            server.createContext("/user", new UserHandlerHttpHandler());
            server.setExecutor(threadPoolExecutor);

            //Start HttpServer
            server.start();
            System.out.println("Server started on port " + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
