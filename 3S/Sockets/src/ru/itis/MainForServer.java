package ru.itis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class MainForServer {
    public static List<EchoServerSocket> sockets;

    public static void main(String[] args) {
        sockets = new LinkedList<>();
        try {
            ServerSocket server = new ServerSocket(7777);
            while (true) {
                Socket client = server.accept();
                sockets.add(new EchoServerSocket(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
