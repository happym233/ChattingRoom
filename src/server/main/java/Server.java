package server.main.java;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Map<String, Socket> users;
    private Integer port;

    public Server () {
        users = new HashMap<>();
        port = 9999;
    }

    public Map<String, Socket> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Socket> users) {
        this.users = users;
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket socket = ss.accept();
                System.out.println("One client have connected");
                ReceiverThread receiverThread = new ReceiverThread(users, socket);
                receiverThread.start();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Server server = new Server();
       server.start();
    }
}
