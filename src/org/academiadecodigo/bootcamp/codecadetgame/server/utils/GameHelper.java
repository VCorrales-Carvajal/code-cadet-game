package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;

import java.util.Random;
import java.util.Set;
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

    public static String welcome() {
        return MsgFormatter.serverMsg(FileHelper.readFile("resources/game-welcome.txt"));
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

    public static int[] getPlayersPositions(Server server) {

        int[] playersPositions = new int[server.getPlayerDispatcherTable().size()];
        Set<String> usernames = server.getPlayerDispatcherTable().keySet();

        int i = 0;
        for (String username : usernames) {
            playersPositions[i] = server.getPlayerDispatcherTable().get(username).getPlayer().getPosition();
            i++;
        }

        return playersPositions;
    }


    public static void updatePlayersPositions(int change, Server server) {

        Set<String> usernames = server.getPlayerDispatcherTable().keySet();
        for (String username : usernames) {
            PlayerDispatcher pd = server.getPlayerDispatcherTable().get(username);
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }

    }

    public static void updateOnePlayerPosition(int change, String username, Server server) {
        int prevPos = server.getPlayerDispatcherTable().get(username).getPlayer().getPosition();
        //Set new position
        server.getPlayerDispatcherTable().get(username).getPlayer().setPosition(prevPos + change);
    }

    public static String displayCowWisdomQuote() {
        throw new UnsupportedOperationException();
    }


    public static String getStringGivenStep(int step, String stringPositive, String stringNegative) {
        if (step > 0) {
            return stringPositive;
        } else {
            return stringNegative;
        }
    }

    public static String informCurrentPlayer(String currentPlayer) {
        return MsgFormatter.serverMsg("It's " + currentPlayer + "'s turn!\n");
    }

    public static String happenedToYou() {
        return ": this event just happened to you: \n";
    }
}
