package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Factory;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.GameHelper;
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

            if (!server.getUsernames().containsKey(userIPAddress)) {
                assignUsername(userIPAddress);//TODO: Change key to username, use HashMap to get PlayerDispatchers

            } else {
                welcomeBack(userIPAddress);
            }

            server.sendMsgToAll(ServerHelper.userJoined(player.getUsername()));

            // Ask first player for Game configurations: 1) Number of players
            if (playerNumber == 1) {

                out.println(MsgFormatter.serverMsg(GameHelper.insertNumOfPlayers()));

                int playerAnswer = Integer.parseInt(in.readLine()); //TODO: Confirm que no és uma String
                server.setNumberOfPlayers(playerAnswer);
                while (playerAnswer < 1 || playerAnswer > ServerHelper.MAX_CONNECTIONS) {

                    out.println(MsgFormatter.serverMsg(GameHelper.insertNumOfPlayers()));

                }
            }

            if (playerNumber == server.getNumberOfPlayers()) {

                server.setGame(Factory.createGame(server));
                server.getGame().start();
                firstMessageToAllPlayers();

            }


            String playerInput;
            while ((playerInput = in.readLine()) != null) {
                //TODO: Update commands to implement from playerInput
                if (playerInput.toLowerCase().equals("/quit")) {

                    sendMsgToAll(ServerHelper.userLeft(player.getUsername()));
                    break;

                } else if (playerInput.contains("/pm@")) {

                    sendPM(playerInput);
                    continue;

                } else if (playerInput.toLowerCase().equals("/commands")) {

                    sendMsg(GameHelper.gameCommands());
                    continue;

                }
               //TODO: Put here method that uses String playerInput
            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void assignUsername(String userIPAddress) throws IOException {
        out.println(ServerHelper.welcome());
        out.println(ServerHelper.askUsername());

        player = Factory.createPlayer(in.readLine().toLowerCase());
        server.getUsernames().put(userIPAddress, player.getUsername());
        out.println(MsgFormatter.serverMsg("Hello " + player.getUsername() + "!"));
        System.out.println(player.getUsername() + " is user with IP address " + userIPAddress);
    }


    private void welcomeBack(String userIPAddress) {
        out.println(MsgFormatter.serverMsg("Welcome back " + player.getUsername() + "!"));
        System.out.println("User with IP address " + userIPAddress +
                "(" + player.getUsername() + ") is back");
    }


    private void firstMessageToAllPlayers() {

        out.println(MsgFormatter.serverMsg("The Game is about to start, mis babies."));
    }


    private void sendMsgToAll(String message) {
        server.sendMsgToAll(message);
        System.out.println(message);
    }


    private void sendPM(String clientMsg) {

        String targetUser = clientMsg.substring(clientMsg.indexOf("@") + 1,
                clientMsg.indexOf(" ")).toLowerCase();
        String msgToTarget = clientMsg.substring(clientMsg.indexOf(" ") + 1);

        synchronized (server.getPlayerDispatcherList()) {

            if (!server.getUsernames().containsValue(targetUser)) {

                sendMsg("Sorry, user " + targetUser + " does not exist");

            } else {

                for (PlayerDispatcher cd : server.getPlayerDispatcherList()) {

                    if (targetUser.equals(cd.getPlayer().getUsername())) {
                        cd.sendMsg(MsgFormatter.formatPm(player.getUsername(), msgToTarget));
                        //break;
                    }

                }
            }

        }
    }

    public Player getPlayer() {
        return player;
    }
}
