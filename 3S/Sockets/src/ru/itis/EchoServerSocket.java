package ru.itis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * 30.11.2020
 * 07. Sockets
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class EchoServerSocket extends Thread{

    private Socket client;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    public EchoServerSocket(Socket client) {
        this.client = client;
        try {
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.toClient = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        start();
    }

    @Override
    public void run() {
        try {
            // читаем сообщения от клиента
            String messageFromClient = fromClient.readLine();
            while (messageFromClient != null) {
                for (EchoServerSocket serverSocket : MainForServer.sockets) {
                    if (serverSocket == this) {
                       continue;
                    }
                    serverSocket.send(messageFromClient);
                }
                messageFromClient = fromClient.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void send(String messageFromClient) {
        toClient.println(messageFromClient);
    }
}
