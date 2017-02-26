package org.academiadecodigo.bootcamp.codecadetgame.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 */
public class Client {
    public static final int PORT = 8080;
    Socket clientSocket;
    private String hostName = "localhost";//"192.168.0.103";
    PrintWriter out;
    BufferedReader in;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    private void start() {
        try {

            clientSocket = new Socket(hostName, PORT);
            System.out.println("Connected to " + clientSocket.getInetAddress().getHostAddress() +
                    " in port " + clientSocket.getPort() + ". Local port: " + clientSocket.getLocalPort());

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread thread = new Thread(new MsgReceiver());
            thread.start();

            // Read message from console
            Scanner scanner = new Scanner(System.in);
            while (true) {
                out.println(scanner.nextLine());
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    private class MsgReceiver implements Runnable {

        @Override
        public void run() {
            try {
                String msgFromServer;
                while ((msgFromServer = in.readLine()) != null) {
                    System.out.println(msgFromServer);
                }
                System.out.println("You were disconnected from the server");
                in.close();
                out.close();
                clientSocket.close();
                System.exit(1);

            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}
