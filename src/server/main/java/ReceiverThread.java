package server.main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ReceiverThread extends Thread{

    private Map<String, Socket> users;
    private Socket socket;
    private String name;

    public ReceiverThread(Map<String, Socket> users, Socket socket) {
        this.users = users;
        this.socket = socket;
    }

    public void add (String user, Socket s) {
        users.put(user,s);
    }

    public void publish (String name, String message) {
        try {
            for (String user: users.keySet()) {
                if (!user.equals(name)) {
                    users.get(user).getOutputStream().write((name + ": " + message + "\n").getBytes(StandardCharsets.UTF_8));
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    @Override
    public void run () {
        System.out.println("Thread started");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line = "";
            line = br.readLine();
            System.out.println(line);
            this.name = line;
            add(name, socket);
            publish(this.name, "connected");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                publish(this.name, line);
            }
            br.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
