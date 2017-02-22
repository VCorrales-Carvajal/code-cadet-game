package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.ServerHelper;

/**
 * Created by codecadet on 2/22/17.
 */
public class GameHelper {


    public static String gameCommands() {
        //TODO: update
        return "\t /quit \t closes your connection to this chat \n" +
                "\t /pm@username \t sends a personal message to username \n" +
                "\t /commands \t lists the commands of this chat \n";

    }


    public static String insertNumOfPlayers() {
        String msg = "Please insert number of Players (min 1, max " +
                ServerHelper.MAX_CONNECTIONS + ")";
        return  msg;
    }

    private int[] shuffleIndex(int lengthArray) {
        //returns int[] with shuffled indexes;
        throw new UnsupportedOperationException();

    }

    public static String renderPlayersPosition(int[] playerCurrentPositions) {
        String[] boneco = new String[3];
        boneco[0] = "|\\(º_º)|";
        boneco[1] = "|  ) )Z|";
        boneco[2] = "|  / \\ |";
        String emptyPosition = "|     |";
        String field = "";

        for (int player = 0; player < playerCurrentPositions.length; player++ ) {
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j <= 10; j++) {
                    if (j == playerCurrentPositions[player]) {
                        field += boneco[i];
                    } else {
                        field += emptyPosition;
                    }
                }
                field += "|SUCCESS \n";
            }
            field += "\n";
        }
        return field;
    }

    public static String displayCowWisdomQuote() {
        throw new UnsupportedOperationException();
    }
}
