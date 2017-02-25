package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by codecadet on 2/22/17.
 */
public class TimeEvent implements ChoosableEvent {

    private final EventType eventType = EventType.TIME_EVENT;
    public static final int LENGTH_TIME_EVENTS = 5;
    private final double probabilityPositive = 0.8;
    private final Server server;
    private BlockingQueue<String[]> queue;

    private String[] questions;
    private String[] positiveConsequence;
    private String[] negativeConsequence;
    private int[] steps;
    private LifeAreas[] lifeAreas;

    private int[] shuffledIndexes;
    private int counterIndex = 0;
    private String currentPlayer;

    public TimeEvent(Server server) {
        this.server = server;
        init();
    }

    @Override
    public void process(String username) {

        if (!username.equals("All")) {
            System.out.println("TimeEvent not processing accordingly");
            return;
        }

        // Display selected statement
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = GameHelper.TimeEvent() + questions[index];
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Listen to all players
        for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
            pd.setActive(true);
            pd.setCurrentEvent(this);
        }

        // Process all player's answer
        processAnswer(index);

        // Increase counter
        counterIndex++;
        if (counterIndex == LENGTH_TIME_EVENTS) {
            counterIndex = 0;
        }

    }

    private void processAnswer(int index) {

        String winner = null; // winner username

        synchronized (queue) {

            while (!queue.isEmpty()) {
                String firstAnswer = queue.poll()[0];

                if (firstAnswer.equals("1")) {
                    winner = queue.poll()[1];
                    break;
                }
            }
        }


        if (winner != null) {

            String sign = (Math.random() > probabilityPositive) ? "+" : "-";

            // Send message to all showing what happened
            server.sendMsgToAll(MsgFormatter.gameMsg(getConsequence(index, sign)));
            server.sendMsgToAll(MsgFormatter.gameMsg(getConsequenceString(winner, index)));

            // Update winner's position

            int step = (sign.equals("+")) ? steps[index] : -steps[index];
            GameHelper.updateOnePlayerPosition(step, winner, server, lifeAreas[index]);

        } else {
            server.sendMsgToAll(MsgFormatter.gameMsg(GameHelper.invalidAnswer()));
        }


    }

    private void init() {

        queue = new ArrayBlockingQueue<String[]>(server.getNumberOfPlayers());

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_TIME_EVENTS);

        questions = new String[LENGTH_TIME_EVENTS];
        positiveConsequence = new String[LENGTH_TIME_EVENTS];
        negativeConsequence = new String[LENGTH_TIME_EVENTS];
        steps = new int[LENGTH_TIME_EVENTS];
        lifeAreas = new LifeAreas[LENGTH_TIME_EVENTS];

        questions[0] = "You invest in a promising tech startup\n" +
                "\t1. I want it!\n";
        positiveConsequence[0] = "The startup becomes the next instagram, you earn a lot of money\n";
        negativeConsequence[0] = "The startup never takes off and you lose your investment\n";
        steps[0] = 1;
        lifeAreas[0] = LifeAreas.MONEY;

        questions[1] = "You go on vacations to a beautiful Caribbean island\n" +
                "\t1. I want it!\n";
        positiveConsequence[1] = "You came back wonderfully tanned. Ronaldo envies you\n";
        negativeConsequence[1] = "You got bitten by a piranha and you lose a toe\n";
        steps[1] = 1;
        lifeAreas[1] = LifeAreas.HAPPINESS;

        questions[2] = "You develop a new app for the android market\n" +
                "\t1. I want it!\n";
        positiveConsequence[2] = "Your app becomes the next Flappy Bird! Success!\n";
        negativeConsequence[2] = "Never trust the android market!\n";
        steps[2] = 1;
        lifeAreas[2] = LifeAreas.MONEY;


    }


    public String getConsequenceString(String username, int index) {


        String lifeAreaConsequence;
        String changeString;
        String direction;

        int step = steps[index];
        String stepString = (step != 0) ? " steps" : " step";
        changeString = GameHelper.getStringGivenStep(step,
                Math.abs(step) + " step forward.", Math.abs(step) + stepString + " back.");

        switch (lifeAreas[index]) {

            case MONEY:
                direction = GameHelper.getStringGivenStep(step,
                        " earned ", " just lost ");
                lifeAreaConsequence = "You" + direction + "money!";
                break;

            case HAPPINESS:
                direction = GameHelper.getStringGivenStep(step,
                        " happier!", " sad.");
                lifeAreaConsequence = "You are" + direction;
                break;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        return username + ": " + lifeAreaConsequence + " " + changeString;

    }

    @Override
    public void chooseAnswer(String answer, String username) {
        synchronized (queue) {
            String[] answerAndUsername = {answer, username};
            queue.offer(answerAndUsername);
        }
    }

    private String getConsequence(int index, String positiveOrNegative) {
        return (positiveOrNegative.equals("+")) ? positiveConsequence[index] : negativeConsequence[index];
    }
}
