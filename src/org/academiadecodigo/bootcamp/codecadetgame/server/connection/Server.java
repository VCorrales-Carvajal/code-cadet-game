package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Game;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Player;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.ServerHelper;

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

    private Map<String, PlayerDispatcher> playerDispatcherTable = new Hashtable<>();
    private List<PlayerDispatcher> playerDispatcherList = new Vector<>();
    private Game game;

    private int numberOfPlayers = 1; //Initialize as one to ask the first player for the actual number
    private int stepsToFinish = 10;

    public void start() {

        int playerNumber = 0;

        ExecutorService pool = Executors.newFixedThreadPool(ServerHelper.MAX_CONNECTIONS);

        try {
            ServerSocket serverSocket = new ServerSocket(ServerHelper.PORT);

            while (true) {

                // Connect to client (blocking operation)
                ServerHelper.waitingForConnection();
                Socket clientSocket = serverSocket.accept();
                ServerHelper.printIPAddress(clientSocket);

                // Restrict number of connections to the number of players to play this game
                playerNumber++;
                if (playerNumber <= numberOfPlayers) {

                    PlayerDispatcher playerDispatcher =
                            new PlayerDispatcher(clientSocket, this, playerNumber);
                    // Create new thread up to capacity of pool
                    pool.submit(playerDispatcher);

                }

            }

        } catch (IOException e) {
            e.getMessage();
        }
    }


    public void sendMsgToAll(String message) {
        synchronized (playerDispatcherTable) {

            Set<String> usernames = playerDispatcherTable.keySet();
            for (String username : usernames) {
                playerDispatcherTable.get(username).sendMsg(message);
            }

        }
    }


    public Map<String, PlayerDispatcher> getPlayerDispatcherTable() {
        return playerDispatcherTable;
    }

    protected int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    protected void setNumberOfPlayers(int number) {
        numberOfPlayers = number;
    }


    public Game getGame() {
        return game;
    }

    protected void setGame(Game game) {
        this.game = game;
    }


    public int getStepsToFinish() {
        return stepsToFinish;
    }

    public void setStepsToFinish(int stepsToFinish) {
        this.stepsToFinish = stepsToFinish;
    }

    public List<PlayerDispatcher> getPlayerDispatcherList() {
        return playerDispatcherList;
    }
}
