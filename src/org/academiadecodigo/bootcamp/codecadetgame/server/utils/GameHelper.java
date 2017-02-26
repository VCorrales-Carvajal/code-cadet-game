package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Player;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.GameLength;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by codecadet on 2/22/17.
 */
public class GameHelper {

    public static final String COLLECTIVE_USERNAME = "All";
    public static final int MAX_TURNS = 30;
    public static final int STEPS_RENDERED = 10;
    public static final double PROB_COW_WISDOM_QUOTE = 0.3;
    public static final long GAME_THREAD_SLEEP = 2000;

    public static String gameCommands() {
        return "\t /quit \t closes your connection to this chat \n";
    }

    /**
     * Auxiliary method to shuffle the indexes of an array
     *
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

    public static String startGame() {
        return MsgFormatter.gameMsg("### GAME START ####\n");
    }

    public static String gettingOutOfAC() {
        return MsgFormatter.gameMsg("You are on your last day of your bootcamp and to graduate you have to answer a question: ");
    }

    public static String welcome() {
        return MsgFormatter.serverMsg(FileHelper.readFile("resources/game-welcome.txt"));
    }

    public static String renderPlayersPosition(int[] playerPositions, String[] usernames) {

        int bonecoLines = 3;
        String[] boneco = new String[bonecoLines];
        boneco[0] = "|\\(º_º)|";
        boneco[1] = "|  ) )Z|";
        boneco[2] = "|  / \\ |";
        String emptyPosition = "|     |";
        String field = "";

        for (int player = 0; player < playerPositions.length; player++) {
            for (int i = 0; i < bonecoLines; i++) {
                for (int j = 1; j <= STEPS_RENDERED; j++) {
                    if (j == playerPositions[player]) {
                        field += boneco[i];
                    } else {
                        field += emptyPosition;
                    }
                }
                field += usernames[player] + "\n";
            }
            field += "\n";
        }

        return MsgFormatter.gameMsg(field + "\n");
    }

    public static int[] getPlayerPositions(Server server) {
        int[] pos = new int[server.getPlayerDispatcherList().size()];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = server.getPlayerDispatcherList().get(i).getPlayer().getGlobalPosition();
        }
        return pos;
    }

    public static void updatePlayersPositions(int change, String username, Server server, LifeArea lifeArea) {

        Player player;
        if (!username.equals(GameHelper.COLLECTIVE_USERNAME)) {

            player = server.getPlayerDispatcherTable().get(username).getPlayer();
            updateOnePlayerPosition(change, player, lifeArea);

        } else {

            for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
                player = pd.getPlayer();
                updateOnePlayerPosition(change, player, lifeArea);
            }
        }

    }

    private static void updateOnePlayerPosition(int change, Player player, LifeArea lifeArea) {

        // Player's position cannot be negative
        if ((player.getGlobalPosition() + change) >= 0) {
            player.setGlobalPosition(player.getGlobalPosition() + change);
        } else {
            player.setGlobalPosition(0);
        }
        // Update lifeArea position
        player.setLifeAreasPosition(player.getLifeAreasPosition()[lifeArea.ordinal()] + change, lifeArea);


    }

    public static String cowWisdomQuote() {
        String quote = (Math.random() > 0.5) ? "      é uma troca!" : "       é lidar!   ";
        String cow =
                "                 __________________________\n" +
                        "         }__{   /                          \\\n" +
                        "         (00)  (" + quote + "          )\n" +
                        "  :****** \\/ ===\\__________________________/\n" +
                        " : #     ##\n" +
                        "   ##****##\n" +
                        "   \"\"    \"\"";

        return MsgFormatter.gameMsg(cow + "\n");
    }

    public static String informCurrentPlayer(String currentPlayer) {
        return MsgFormatter.gameMsg("It's <" + currentPlayer + ">'s turn! Please press <Enter> to roll the dice\n");
    }

    public static String personalEvent(String username) {
        return "######## <" + username + "> - this just happened to you:\n";
    }

    public static String collectiveEvent() {
        return "******** ALL - This just happened: \n";
    }

    public static String lifeDecision(String username) {
        return "######## <" + username + "> - " +
                "You now have to take a life decision:\n";
    }

    public static String JAVAQuestion() {
        return "******** ALL - Let's test your knowledge of Java:\n";
    }


    public static String TimeEvent() {
        return "******** ALL - First to choose takes it! Think fast!\n";
    }

    public static String informLifeAreaAffected(String username, int step, LifeArea lifeArea, EventType eventType) {

        boolean isCollective = username.equals(COLLECTIVE_USERNAME);
        boolean isStepForward = (step > 0);

        String lifeAreaConsequence;
        switch (lifeArea) {
            case CAREER:
                lifeAreaConsequence = (isCollective)
                        ? ((isStepForward) ? "Everyone is advancing in their career!" : "Everyone has a setback in their career!")
                        : ((isStepForward) ? "You advanced in your career!" : "You had a setback in your career!");
                break;

            case MONEY:
                lifeAreaConsequence = (isCollective)
                        ? ((isStepForward) ? "Everyone earns money!" : "Everyone lost money!")
                        : ((isStepForward) ? "You earned money!" : "You lost money!");
                break;

            case HAPPINESS:
                lifeAreaConsequence = (isCollective)
                        ? ((isStepForward) ? "Everyone is happier!" : "Everyone is sad.")
                        : ((isStepForward) ? "You are happier!" : "You are sad.");
                break;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        String stepString = (step == 1) ? " step" : " steps";

        String changeString = (isStepForward) ? " " + Math.abs(step) + stepString + " forward." :
                " " + Math.abs(step) + stepString + " back.";// n step/steps forward/back.

        String target = (!isCollective) ? username + ": " : "";

        return MsgFormatter.gameMsg(target + lifeAreaConsequence + changeString + "\n");
    }

    public static String invalidAnswer() {
        return MsgFormatter.gameMsg("Invalid Answer. No one moves this turn.\n");
    }

    public static String informLifeAreaPosition(Server server, String[] usernames) {
        String result = "Let's see how life looks: \n";
        for (int j = 0; j < usernames.length; j++) {
            result = result + "<" + usernames[j] + "> ";
            for (int i = 0; i < LifeArea.values().length; i++) {
                result = result + " " + LifeArea.values()[i] + ": " + server.getPlayerDispatcherTable().get(usernames[j]).getPlayer().getLifeAreasPosition()[i] + ". ";
            }
            result = result + "\n";
        }
        return MsgFormatter.gameMsg(result + "\n");
    }

    public static String endGame() {
        return MsgFormatter.gameMsg("### GAME OVER ###");
    }

    public static String informWinner(String winner) {
        return MsgFormatter.gameMsg("----- " + winner + " is the winner -----");
    }
}
