package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Factory;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Game;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Player;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.ChoosableEvent;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.GameLength;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.ServerHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 */

public class PlayerDispatcher implements Runnable {

    private Player player;
    private Server server;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int playerNumber;
    private boolean active;
    private ChoosableEvent currentEvent;
    private GameLength gameLength;

    public PlayerDispatcher(Socket clientSocket, Server server, int playerNumber) {

        this.clientSocket = clientSocket;
        this.server = server;
        this.playerNumber = playerNumber;

    }

    public void sendMsg(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            // Setup streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Ask player for username
            out.println(ServerHelper.welcome(playerNumber, server.getNumberOfPlayers()));
            out.println(ServerHelper.askUsername());
            String username = getUsernameFromPlayer();

            // Add Player Dispatcher to the server table (container of player dispatchers)
            server.getPlayerDispatcherTable().put(username, this);
            server.getPlayerDispatcherList().add(this);

            // Create player
            player = Factory.createPlayer(username);

            // Inform others that a new player has joined
            server.sendMsgToAll(ServerHelper.userJoined(player.getUsername()));

            // Show user IPAddress
            String userIPAddress = clientSocket.getInetAddress().getHostAddress();
            ServerHelper.informIPAddress(player.getUsername(), userIPAddress);

            // Ask first player for Game configurations: Nº of players & Game length
            if (playerNumber == 1) {

                // 1) Number of players?
                int numPlayersAnswer = askNumberOfPlayers();
                server.setNumberOfPlayers(numPlayersAnswer);

                // 2) Game length?
                int gameLength = askNumberOfSteps();
                server.setStepsToFinish(gameLength);

                out.println(ServerHelper.waitingForOtherPlayersToConnect());

            }

            // When all players have connected, start the game
            out.println("You are player Nº" + playerNumber + " in a game of " + server.getNumberOfPlayers() + " players");
            out.println(playerNumber == server.getNumberOfPlayers());
            if (playerNumber == server.getNumberOfPlayers()) {
                out.println("You are the last player (Nº " + playerNumber + "). You are about to start the Game");
//                server.setGame(Factory.createGame(server));
                Game game = new Game(server);
                server.setGame(game);
                out.println("You have created a new " + game.toString());
                out.println("After injecting game to server. You have created a new " + server.getGame().toString());
                Thread thread = new Thread(server.getGame());
                thread.setName("game");
                thread.start();

                server.sendMsgToAll((ServerHelper.startGame()));

            }

            // Get player input throughout the game
            String playerInput;
            while (((playerInput = in.readLine()) != null) && active) {

                if (playerInput.toLowerCase().equals("/quit")) {
                    sendMsgToAll(ServerHelper.userLeft(player.getUsername()));
                    break;
                }

                currentEvent.chooseAnswer(playerInput, player.getUsername());

                active = false;

            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int askNumberOfSteps() throws IOException {

        out.println(ServerHelper.insertNumberOfSteps());

        int playerAnswer = Integer.parseInt(in.readLine());
        while (playerAnswer < 1 || playerAnswer > (GameLength.values().length - 1)) {
            out.println(ServerHelper.insertNumOfPlayers());
        }

        return GameLength.values()[playerAnswer - 1].getNumberOfSteps();
    }

    private int askNumberOfPlayers() throws IOException {

        out.println(ServerHelper.insertNumOfPlayers());

        int playerAnswer = Integer.parseInt(in.readLine());
        while (playerAnswer < 1 || playerAnswer > ServerHelper.MAX_CONNECTIONS) {
            out.println(ServerHelper.insertNumOfPlayers());
        }

        return playerAnswer;

    }

    private String getUsernameFromPlayer() throws IOException {

        String usernameInserted = in.readLine().toLowerCase();
        while (server.getPlayerDispatcherTable().containsKey(usernameInserted)) {
            out.println(ServerHelper.userExistsTryAgain());
            usernameInserted = in.readLine().toLowerCase();
        }

        return usernameInserted;
    }

    private void sendMsgToAll(String message) {
        server.sendMsgToAll(message);
        System.out.println(message);
    }

    public Player getPlayer() {
        return player;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCurrentEvent(ChoosableEvent currentEvent) {
        this.currentEvent = currentEvent;
    }
}
