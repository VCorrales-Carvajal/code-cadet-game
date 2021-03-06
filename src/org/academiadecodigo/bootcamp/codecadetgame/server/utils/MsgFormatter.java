package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

/**
 * Created by codecadet on 2/18/17.
 */
public class MsgFormatter {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static String serverMsg(String msg) {
        return ANSI_YELLOW + msg + ANSI_RESET;
    }

    public static String gameMsg(String msg) {
        return ANSI_GREEN + msg + ANSI_RESET;
    }

    public static String collectiveMessage(String msg){
        return ANSI_BLUE_BACKGROUND + ANSI_WHITE + msg + ANSI_RESET;
    }

    public static String personalMessage(String msg){
        return ANSI_YELLOW_BACKGROUND + ANSI_BLACK + msg + ANSI_RESET;
    }

    public static String endGameMsg(String msg) {
        return ANSI_PURPLE + msg + ANSI_RESET;
    }

    public static String highlightUsername(String msg) {
        return ANSI_YELLOW + msg + ANSI_RESET;
    }

    public static String globalPosition(String msg) {
        return ANSI_YELLOW_BACKGROUND + ANSI_BLACK + msg + ANSI_RESET;
    }

    public static String turnWinner(String msg) {
        return ANSI_YELLOW + msg + ANSI_RESET;
    }

    public static String playerPos(String msg) {
        return ANSI_YELLOW + msg + ANSI_RESET;
    }

}
