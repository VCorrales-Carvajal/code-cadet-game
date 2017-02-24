package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by codecadet on 2/22/17.
 */
public class TimeEvent implements ChoosableEvent  {

    public static final int LENGTH_TIME_EVENTS = 5;
    private final Server server;
    private BlockingQueue<String> queue = new SynchronousQueue<>();

    String[] questions;
    String[] positiveConsequence;
    String[] negativeConsequence;
    int[] steps;
    LifeAreas[] lifeAreas;

    private int[] shuffledIndexes;
    private int counterIndex = 0;

    public TimeEvent(Server server) {
        this.server = server;
        init();
    }

    @Override
    public void process(String username) {

        if (!username.equals("All")){
            System.out.println("TimeEvent not processing accordingly");
            return;
        }

        // Display selected statement
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = GameHelper.TimeEvent() + questions[index];
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Listen to all players
        for (PlayerDispatcher pd: server.getPlayerDispatcherList()) {
            pd.setActive(true);
            pd.setCurrentEvent(this);
        }

        // Process all player's answer
        processAnswer(index);

    }

    private void processAnswer(int index) {
        //TODO Micael: Get the winner and save it in the String winner.
        String firstAnswer = queue.poll();

        String winner = null; // winner username

        // Update winner's position
        GameHelper.updateOnePlayerPosition(steps[index], winner, server, lifeAreas[index]);

        // Send message to all showing what happened
        server.sendMsgToAll(getConsequenceString(winner, index));
    }

    private void init(){

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_TIME_EVENTS);

        questions = new String[LENGTH_TIME_EVENTS];
        positiveConsequence = new String[LENGTH_TIME_EVENTS];
        negativeConsequence = new String[LENGTH_TIME_EVENTS];
        steps = new int[LENGTH_TIME_EVENTS];
        lifeAreas = new LifeAreas[LENGTH_TIME_EVENTS];

        questions[0] = "You invest in a promising tech startup\n" +
                "\t1. Yes\n" +
                "\t2. No\n";
        positiveConsequence[0] = "The startup becomes the next instagram, you earn a lot of money";
        negativeConsequence[0] = "The startup never takes off and you lose your investment";
        steps[0] = 1;
        lifeAreas[0] = LifeAreas.MONEY;

        questions[1] = "You go on vacations to a beautiful Caribbean island\n" +
                "\t1. Yes\n" +
                "\t2. No\n";
        positiveConsequence[1] = "You came back wonderfully tanned. Ronaldo envies you";
        negativeConsequence[1] = "You got bitten by a piranha and you lose a toe";
        steps[1] = 1;
        lifeAreas[1] = LifeAreas.HAPPINESS;


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
    public void chooseAnswer(String answer) {
        synchronized (this) {
            queue.offer(answer);
        }
    }
}
