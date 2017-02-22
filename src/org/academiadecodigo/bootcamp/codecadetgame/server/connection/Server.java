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
    private final List<PlayerDispatcher> playerDispatcherList;
    private Hashtable<String, String> usernames;
    private int numberOfPlayers = 1; //Initialize as one to ask the first player for the actual number
    private Game game;

    public Server() {

        playerDispatcherList = Collections.synchronizedList(new LinkedList<>());
        usernames = new Hashtable<>();

    }

    public void start() {

        int playerNumber = 0;

        ExecutorService pool = Executors.newFixedThreadPool(ServerHelper.MAX_CONNECTIONS);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                // Connect to client (blocking operation)
                System.out.println("Waiting for player connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player with IP address " +
                        clientSocket.getInetAddress().getHostAddress() +
                        " is now connected");

                // Restrict number of connections to the number of players to play this game
                playerNumber++;
                if (playerNumber <= numberOfPlayers) {

                    PlayerDispatcher playerDispatcher =
                            new PlayerDispatcher(clientSocket, this, playerNumber);
                    playerDispatcherList.add(playerDispatcher);

                    // Create new thread up to capacity of pool
                    pool.submit(playerDispatcher);

                }

            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void sendMsgToAll(String message) {
        synchronized (playerDispatcherList) {
            for (PlayerDispatcher playerDispatcher : playerDispatcherList) {
                playerDispatcher.sendMsg(message);
            }
        }

    }

    protected int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    protected void setNumberOfPlayers(int number) {
        numberOfPlayers = number;
    }

    //TODO: Change List of playerDispatchers to HashMap and remove the use of the HashTable with usernames
    protected Hashtable<String, String> getUsernames() {
        return usernames;
    }

    public List<PlayerDispatcher> getPlayerDispatcherList() {
        return playerDispatcherList;
    }

    public Game getGame() {
        return game;
    }

    protected void setGame(Game game) {
        this.game = game;
    }



}
