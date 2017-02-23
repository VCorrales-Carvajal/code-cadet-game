package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 */
public class Server {
    public static final int PORT = 8080;
    public static final int MAX_PLAYERS = 4;
    private Map<String, PlayerDispatcher> playerDispatcherTable = new Hashtable<>();
    private int playersInThisGame = 1;
    private int stepsToFinish = 10;
    private Game game;

    public void start() {

        int playerNumber = 0;

        ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                // Connect to client (blocking operation)
                System.out.println("Waiting for player connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player with IP address " +
                        clientSocket.getInetAddress().getHostAddress() +
                        " is now connected");

                // Create new thread with new player
                playerNumber++;
                if (playerNumber <= playersInThisGame) {

                    PlayerDispatcher playerDispatcher = new PlayerDispatcher(clientSocket, this, playerNumber);

                    // Create new thread up to capacity of pool
                    pool.submit(playerDispatcher);

                }

            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void sendPM(String msg, PlayerDispatcher sender) {

        String targetPlayer = msg.substring(msg.indexOf("@") + 1,
                msg.indexOf(" ")).toLowerCase();
        String msgToTarget = msg.substring(msg.indexOf(" ") + 1);

        synchronized (playerDispatcherTable) {

            if (!playerDispatcherTable.containsKey(targetPlayer)) {

                sender.sendMsg("Sorry, user " + targetPlayer + " does not exist");

            } else {

                playerDispatcherTable.get(targetPlayer).sendMsg(MsgHelper.pm(sender.getPlayer().getUsername(), msgToTarget));

            }

        }
    }

    protected void setGame(Game game) {

        this.game = game;
    }

    protected int getPlayersInThisGame() {

        return playersInThisGame;

    }

    protected int setPlayersInThisGame(int number) {

        return playersInThisGame = number;

    }

    public Game getGame() {
        return game;
    }

    public void sendMsgToAll(String message) {
        synchronized (playerDispatcherTable) {

            Set<String> usernames = playerDispatcherTable.keySet();

            for (String username : usernames) {
                playerDispatcherTable.get(username).sendMsg(message);
            }

        }

    }

    public int getNumPlayersInGame() {
        return playersInThisGame;
    }

    public int getStepsToFinish() {
        return stepsToFinish;
    }

    public void setStepsToFinish(int stepsToFinish) {
        this.stepsToFinish = stepsToFinish;
    }

    public Map<String, PlayerDispatcher> getPlayerDispatcherTable() {
        return playerDispatcherTable;
    }
}
