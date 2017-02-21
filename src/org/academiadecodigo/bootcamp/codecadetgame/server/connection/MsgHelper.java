package org.academiadecodigo.bootcamp.codecadetgame.server.connection;

/**
 * Created by codecadet on 2/18/17.
 */
public class MsgHelper {
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

    public static String commands() {
        //TODO: update
        return "\t /quit \t closes your connection to this chat \n" +
                "\t /pm@username \t sends a personal message to username \n" +
                "\t /commands \t lists the commands of this chat \n";

    }

    public static String clientMsg(String username, String clientMsg) {
        return "< " + ANSI_YELLOW + username + ANSI_RESET + " > " + ANSI_CYAN +
                clientMsg + ANSI_RESET;
    }

    public static String pm(String username, String msgToTarget) {
        return ANSI_YELLOW_BACKGROUND + ANSI_BLACK +
                "PM< " + username + " > " + msgToTarget +
                ANSI_RESET;
    }

    public static String serverMsg(String msg) {
        return ANSI_BLACK_BACKGROUND + ANSI_YELLOW +
                msg + ANSI_RESET;
    }

    public static void displayCowWisdomQuote() {
        throw new UnsupportedOperationException();
    }

    public static String displayPlayersPosition(int[] playerCurrentPositions) {
        String[] boneco = new String[3];
        boneco[0] = "|\\(º_º)|";
        boneco[1] = "|  ) )Z|";
        boneco[2] = "|  / \\ |";
        String emptyPosition = "|     |";
        String field = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 10; j++) {
                if (j == playerCurrentPositions[0]) {
                    //TODO To: fori for all positions
                    field += boneco[i];
                } else {
                    field += emptyPosition;
                }
            }
            field += "\n";
        }

        return field;
    }
}
