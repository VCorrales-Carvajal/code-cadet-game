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

            if (!server.getUsernames().containsKey(userIPAddress)) {
                assignUsername(userIPAddress);

            } else {
                welcomeBack(userIPAddress);
            }

            initialMsg();

            if (playerNumber == 1) {

                msgToFirstPlayer();
                int playerAnswer = Integer.parseInt(in.readLine()); //TODO: Confirm que no és uma String
                server.setPlayersInThisGame(playerAnswer);
                while (playerAnswer < 1 || playerAnswer > 4) {

                    msgToFirstPlayer();

                }
            }

            if (playerNumber == server.getPlayersInThisGame()) {

                server.setGame(Factory.createGame(server));
                firstMessageToAllPlayers();

            }


            String clientMsg;
            while ((clientMsg = in.readLine()) != null) {

                if (clientMsg.toLowerCase().equals("/quit")) {

                    sendMsgToAll(MsgHelper.serverMsg(player.getUsername() + " has left the chat"));
                    break;

                } else if (clientMsg.contains("/pm@")) {

                    sendPM(clientMsg);
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

    private void assignUsername(String userIPAddress) throws IOException {
        out.println(MsgHelper.serverMsg("Welcome to Scarlet chat. What's your username?"));
        player = Factory.createPlayer(in.readLine().toLowerCase());
        server.getUsernames().put(userIPAddress, player.getUsername());
        out.println(MsgHelper.serverMsg("Hello " + player.getUsername() + "!"));
        System.out.println(player.getUsername() + " is user with IP address " + userIPAddress);
    }


    private void welcomeBack(String userIPAddress) {
        out.println(MsgHelper.serverMsg("Welcome back " + player.getUsername() + "!"));
        System.out.println("User with IP address " + userIPAddress +
                "(" + player.getUsername() + ") is back");
    }

    private void msgToFirstPlayer() {

        out.println(MsgHelper.serverMsg("Please insert number of Players - minimum one, max four"));
    }

    private void firstMessageToAllPlayers() {

        out.println(MsgHelper.serverMsg("The Game is about to start, mis babies."));
    }

    private void initialMsg() {
        out.println(MsgHelper.serverMsg("Use the keyword /commands to see the commands of this chat"));
        server.sendMsgToAll(MsgHelper.serverMsg("< " + player.getUsername() + " > has joined the chat"));
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
                        cd.sendMsg(MsgHelper.pm(player.getUsername(), msgToTarget));
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
