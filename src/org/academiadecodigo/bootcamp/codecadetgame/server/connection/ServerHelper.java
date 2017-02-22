package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

/**
 * Created by codecadet on 2/22/17.
 */
public class ServerHelper {

    public static final int MAX_CONNECTIONS = 4;

    public static String welcome() {
        return MsgHelper.serverMsg("Welcome to our fantastic game server!");
    }

    public static String askUsername() {
        return MsgHelper.serverMsg("What's your username?");
    }

    public static String userJoined(String username) {
        return MsgHelper.serverMsg("< " + username + " > has joined");
    }

    public static String userLeft(String username) {
        return MsgHelper.serverMsg(username + " has left the game");
    }
}
