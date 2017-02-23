package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Factory;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Player;

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

    public PlayerDispatcher(Socket clientSocket, Server server, int playerNumber) {

        this.clientSocket = clientSocket;
        this.server = server;
        this.playerNumber = playerNumber;
        init();

    }

    public void sendMsg(String message) {
        out.println(message);
    }

    private void init() {

        // Setup streams
        try {

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void run() {
        try {
            //Send welcome message to client
            String userIPAddress = clientSocket.getInetAddress().getHostAddress();

            // Ask player for username
            out.println(MsgHelper.serverMsg("Welcome to our fantastic game server! What's your username?"));
            String username = getUsernameFromPlayer();

            // Add Player Dispatcher to the server "list" (map)
            server.getPlayerDispatcherTable().put(username, this);

            // Create player
            player = Factory.createPlayer(username);

            // Welcome and inform others new player joined
            out.println(MsgHelper.serverMsg("Hello " + player.getUsername() + "!"));
            System.out.println(player.getUsername() + " is user with IP address " + userIPAddress);
            server.sendMsgToAll(MsgHelper.serverMsg("< " + player.getUsername() + " > has joined"));

            if (playerNumber == 1) {

                msgToFirstPlayer();
                int playerAnswer = Integer.parseInt(in.readLine()); //TODO: Confirm que no és uma String
                server.setPlayersInThisGame(playerAnswer);
                while (playerAnswer < 1 || playerAnswer > 4) {

                    msgToFirstPlayer();

                }
                //TODO Antonio: Ask for gamelength and set server.setStepsToFinish
            }

            if (playerNumber == server.getPlayersInThisGame()) {

                server.setGame(Factory.createGame(server));
                server.getGame().start();
                firstMessageToAllPlayers();

            }


            String clientMsg;
            while ((clientMsg = in.readLine()) != null) {

                if (clientMsg.toLowerCase().equals("/quit")) {

                    sendMsgToAll(MsgHelper.serverMsg(player.getUsername() + " has left the chat"));
                    break;

                } else if (clientMsg.contains("/pm@")) {

                    server.sendPM(clientMsg, this);
                    continue;

                } else if (clientMsg.toLowerCase().equals("/commands")) {

                    sendMsg(MsgHelper.commands());
                    continue;

                }

                sendMsgToAll(MsgHelper.clientMsg(player.getUsername(), clientMsg));
            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getUsernameFromPlayer() throws IOException {
        String usernameInserted = in.readLine().toLowerCase();
        while (server.getPlayerDispatcherTable().containsKey(usernameInserted)) {
            out.println("That username already exist, please insert another one");
            usernameInserted = in.readLine().toLowerCase();
        }
        return usernameInserted;
    }


    private void msgToFirstPlayer() {

        out.println(MsgHelper.serverMsg("Please insert number of Players - minimum one, max four"));
    }

    private void firstMessageToAllPlayers() {

        out.println(MsgHelper.serverMsg("The Game is about to start, mis babies."));
    }


    private void sendMsgToAll(String message) {
        server.sendMsgToAll(message);
        System.out.println(message);
    }




    public Player getPlayer() {
        return player;
    }
}
