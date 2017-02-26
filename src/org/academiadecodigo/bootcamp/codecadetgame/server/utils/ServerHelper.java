package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.GameLength;

import java.net.Socket;

/**
 * Created by codecadet on 2/22/17.
 */
public class ServerHelper {

    public static final int MAX_CONNECTIONS = 4;
    public static final int PORT = 8080;

    public static void printIPAddress(Socket clientSocket) {
        System.out.println("Player with IP address " +
                clientSocket.getInetAddress().getHostAddress() +
                " is now connected");

    }

    public static void waitingForConnection() {
        System.out.println("Waiting for player connection...");
    }

    public static String welcome() {
        return MsgFormatter.serverMsg(FileHelper.readFile("resources/game-welcome.txt"));
    }

    public static String askUsername() {
        return MsgFormatter.serverMsg("What's your username?");
    }

    public static String userJoined(String username) {
        return MsgFormatter.serverMsg("< " + username + " > has joined the game\n");
    }

    public static void informIPAddress(String username, String IPAddress) {
        System.out.println(username + " is user with IP address " + IPAddress);
    }

    public static String insertNumOfPlayers() {
        String msg = MsgFormatter.serverMsg("Please insert number of players (min 1, max " +
                ServerHelper.MAX_CONNECTIONS + "):");
        return msg;
    }


    public static String userLeft(String username) {
        return MsgFormatter.serverMsg(username + " has left the game");
    }

    public static String userExistsTryAgain() {
        return MsgFormatter.serverMsg("That username already exist, please insert another one");
    }


    public static String insertNumberOfSteps() {

        String numberOfSteps = MsgFormatter.serverMsg("Please insert your wished Game Length (" + showGameLength() + "):");
        return numberOfSteps;
    }

    private static String showGameLength() {
        String s = "";
        for (int i = 0; i < GameLength.values().length; i++) {
            s = s +(GameLength.values()[i].ordinal() + 1) + "-" + GameLength.values()[i].toString();
            if (i != (GameLength.values().length - 1)) {
                s = s + ", ";
            }
        }
        return s;
    }

    public static String waitingForOtherPlayersToConnect() {
        return MsgFormatter.serverMsg("\nWaiting for the other players to connect...");
    }
}
