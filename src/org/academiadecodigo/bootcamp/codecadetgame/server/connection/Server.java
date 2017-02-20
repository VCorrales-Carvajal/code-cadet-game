package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Game;

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
    private final List<PlayerDispatcher> playerDispatcherList;
    private Hashtable<String, String> usernames;
    private int playersInThisGame = 1;
    private Game game;

    public Server() {

        playerDispatcherList = Collections.synchronizedList(new LinkedList<>());
        usernames = new Hashtable<>();

    }

    public void start() {

        int playerNumber = 0;

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
                playerNumber++;
                if (playerNumber <= playersInThisGame) {

                    PlayerDispatcher playerDispatcher = new PlayerDispatcher(clientSocket, this, playerNumber);
                    playerDispatcherList.add(playerDispatcher);

                    // Create new thread up to capacity of pool
                    pool.submit(playerDispatcher);

                }

            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    protected void setGame(Game game){

        this.game = game;
    }

    protected int getPlayersInThisGame() {

        return playersInThisGame;

    }

    protected int setPlayersInThisGame(int number) {

        return playersInThisGame = number;

    }

    protected Hashtable<String, String> getUsernames() {
        return usernames;
    }

    protected List<PlayerDispatcher> getPlayerDispatcherList() {
        return playerDispatcherList;
    }

    public Game getGame() {
        return game;
    }

    public void sendMsgToAll(String message) {
        synchronized (playerDispatcherList) {
            for (PlayerDispatcher playerDispatcher : playerDispatcherList) {
                playerDispatcher.sendMsg(message);
            }
        }

    }

    public int getNumPlayersInGame() {
        return playersInThisGame;
    }
}
