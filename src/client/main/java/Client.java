package client.main.java;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {

    private String name;

    private InetAddress serverIa;

    private JTextArea chattingMessage;

    private BufferedReader reader;

    private PrintWriter writer;

    private static Integer port=9999;

    public Client(String name, String serverIaName, JTextArea chattingMessage) {
        this.chattingMessage = chattingMessage;
        // System.out.println("name: " + name + " server ip:  " + serverIaName);
        try {
            if (name.isEmpty()) {
                throw new Exception("Undefined name");
            }
            this.name = name;
            if (serverIaName.isEmpty()) {
                this.serverIa = InetAddress.getByName("localhost");
            } else {
                this.serverIa = InetAddress.getByName(serverIaName);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getServerIa() {
        return serverIa;
    }

    public void setServerIa(InetAddress serverIa) {
        this.serverIa = serverIa;
    }

    public void start() {
        new Thread() {
           @Override
           public void run() {
               try {
                   Socket socket = new Socket(serverIa, port);
                   writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                   reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   writer.write(name+"\n");
                   writer.flush();
                   String line=null;
                   while ((line = reader.readLine()) != null) {
                       chattingMessage.append(line + "\n");
                   }
                   writer.close();
                   reader.close();
                   writer = null;
                   reader = null;
               } catch (Throwable t) {
                   t.printStackTrace();
               }
           }
        }.start();
    }


    public void send(String msg)  {
        if (writer != null) {
            writer.write(msg + "\n");
            try {
                Thread.sleep(500);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            writer.flush();
        } else {
            chattingMessage.append("Connection closed");
        }
    }
}
