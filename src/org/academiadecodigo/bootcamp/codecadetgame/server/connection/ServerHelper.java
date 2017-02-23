package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

/**
 * Created by codecadet on 2/22/17.
 */
public class ServerHelper {

    public static final int MAX_CONNECTIONS = 4;

    public static String welcome() {
        return MsgFormatter.serverMsg("Welcome to our fantastic game server!");
    }

    public static String askUsername() {
        return MsgFormatter.serverMsg("What's your username?");
    }

    public static String userJoined(String username) {
        return MsgFormatter.serverMsg("< " + username + " > has joined");
    }

    public static String userLeft(String username) {
        return MsgFormatter.serverMsg(username + " has left the game");
    }
}
