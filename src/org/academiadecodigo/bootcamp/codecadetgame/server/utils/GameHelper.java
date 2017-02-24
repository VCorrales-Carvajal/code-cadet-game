package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by codecadet on 2/22/17.
 */
public class GameHelper {

    public static String gameCommands() {
        return "\t /quit \t closes your connection to this chat \n";
    }

    /**
     * Auxiliary method to shuffle the indexes of an array
     * @param length of the array
     * @return int[] with randomly ordered numbers that ranging from 0 until (length - 1)
     */
    public static int[] shuffleIndexArray(int length) {
        // Implementing Fisher-Yates shuffle
        int[] ar = new int[length];
        for (int i = 0; i < length; i++) {
           ar[i] = i;
        }

        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    public static String gettingOutOfAC() {
        return MsgFormatter.serverMsg("You are on your last day of your bootcamp and to graduate you have to answer a question: ");
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
