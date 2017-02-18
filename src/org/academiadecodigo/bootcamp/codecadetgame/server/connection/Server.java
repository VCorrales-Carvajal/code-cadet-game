package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 */
public class Server {
    public static final int PORT = 8080;
    public static final int MAX_PLAYERS = 4;
    private final List<PlayerDispatcher> playerDispatchers;
    private Hashtable<String, String> usernames;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public Server() {

        playerDispatchers = Collections.synchronizedList(new LinkedList<>());
        usernames = new Hashtable<>();

    }

    private void start() {

        ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                // Connect to client (blocking operation)
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client with IP address " +
                        clientSocket.getInetAddress().getHostAddress() +
                        " is now connected");

                // Add client dispatcher to container
                PlayerDispatcher playerDispatcher = new PlayerDispatcher(clientSocket, this);
                playerDispatchers.add(playerDispatcher);

                // Create new thread up to capacity of pool
                pool.submit(playerDispatcher);

            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    protected Hashtable<String,String> getUsernames() {
        return usernames;
    }

    protected List<PlayerDispatcher> getPlayerDispatchers() {
        return playerDispatchers;
    }

    protected void sendMsgToAll(String message) {
        synchronized (playerDispatchers) {
            for (PlayerDispatcher playerDispatcher : playerDispatchers) {
                playerDispatcher.sendMsg(message);
            }
        }

    }
}
