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
        return MsgFormatter.serverMsg("Welcome to our fantastic game server!");
    }

    public static String askUsername() {
        return MsgFormatter.serverMsg("What's your username?");
    }

    public static String userJoined(String username) {
        return MsgFormatter.serverMsg("< " + username + " > has joined");
    }

    public static void informIPAddress(String username, String IPAddress) {
        System.out.println(username + " is user with IP address " + IPAddress);
    }

    public static String insertNumOfPlayers() {
        String msg = MsgFormatter.serverMsg("Please insert number of Players (min 1, max " +
                ServerHelper.MAX_CONNECTIONS + ")");
        return msg;
    }

    public static String startGame() {
        return MsgFormatter.serverMsg("The Game is about to start, mis babies.");
    }

    public static String userLeft(String username) {
        return MsgFormatter.serverMsg(username + " has left the game");
    }

    public static String userExistsTryAgain() {
        return MsgFormatter.serverMsg("That username already exist, please insert another one");
    }


    public static String insertNumberOfSteps() {

        String numberOfSteps = MsgFormatter.serverMsg("Please insert your wished Game Length: \n " + showGameLength());
        return numberOfSteps;
    }

    private static String showGameLength() {
        String s = "";
        for (int i = 0; i < GameLength.values().length; i++) {
            s = s + (GameLength.values()[i].ordinal() + 1) + ". " + GameLength.values()[i].toString() + "\n ";
        }
        return s;
    }
}
