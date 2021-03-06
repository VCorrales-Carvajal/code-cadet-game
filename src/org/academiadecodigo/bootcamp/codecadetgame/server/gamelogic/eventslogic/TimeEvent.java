package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by codecadet on 2/22/17.
 */
public class TimeEvent implements ChoosableEvent {

    private final EventType eventType = EventType.TIME_EVENT;
    public static final int LENGTH_TIME_EVENTS = 8;
    private final double probabilityPositive = 0.8;
    private String[] questions;
    private final Server server;
    private BlockingQueue<String[]> queue;

    private String[] positiveConsequences;
    private String[] negativeConsequences;
    private int[] steps;
    private LifeArea[] lifeAreas;

    private int[] shuffledIndexes;
    private int counterIndex = 0;
    private String currentPlayer;

    public TimeEvent(Server server) {
        this.server = server;
        init();
    }

    @Override
    public void process(String username) {

        if (!username.equals(GameHelper.COLLECTIVE_USERNAME)) {
            System.out.println("TimeEvent not processing accordingly");
            return;
        }

        // Display selected statement
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = GameHelper.displayEventType(username, eventType) + questions[index];
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

        synchronized (this) {
            while (queue.size() != server.getNumberOfPlayers()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //Thread.interrupt called, no handling needed
                }
            }
        }


        while (!queue.isEmpty()) {

            String[] s = queue.poll();
            String firstAnswer = s[0];

            if (firstAnswer.equals("1")) {
                winner = s[1];
                break;
            }
        }
        queue.clear();

        if (winner != null) {

            // Send message to all showing what happened
            String sign = (Math.random() < probabilityPositive) ? "+" : "-";
            int step = (sign.equals("+")) ? steps[index] : -steps[index];

            server.sendMsgToAll(MsgFormatter.gameMsg(getConsequence(index, sign)));
            server.sendMsgToAll(GameHelper.informLifeAreaAffected(winner, step, lifeAreas[index], eventType));

            // Update winner's position
            GameHelper.updatePlayersPositions(step, winner, server, lifeAreas[index]);

        } else {

            server.sendMsgToAll(MsgFormatter.gameMsg(GameHelper.invalidAnswer()));

        }

    }

    private void init() {

        queue = new ArrayBlockingQueue<String[]>(server.getNumberOfPlayers());

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_TIME_EVENTS);

        questions = new String[LENGTH_TIME_EVENTS];
        positiveConsequences = new String[LENGTH_TIME_EVENTS];
        negativeConsequences = new String[LENGTH_TIME_EVENTS];
        steps = new int[LENGTH_TIME_EVENTS];
        lifeAreas = new LifeArea[LENGTH_TIME_EVENTS];

        questions[0] = "You invest in a promising tech startup\n" +
                "\t1. I want it!\n";
        positiveConsequences[0] = "The startup becomes the next instagram, you earn a lot of money\n";
        negativeConsequences[0] = "The startup never takes off and you lose your investment\n";
        steps[0] = 1;
        lifeAreas[0] = LifeArea.MONEY;

        questions[1] = "You go on vacation to a beautiful Caribbean island\n" +
                "\t1. I want it!\n";
        positiveConsequences[1] = "You came back wonderfully tanned. Ronaldo envies you\n";
        negativeConsequences[1] = "You got bitten by a piranha and you lose a toe\n";
        steps[1] = 1;
        lifeAreas[1] = LifeArea.HAPPINESS;

        questions[2] = "You develop a new app for the android market\n" +
                "\t1. I want it!\n";
        positiveConsequences[2] = "Your app becomes the next Flappy Bird! Success!\n";
        negativeConsequences[2] = "Never trust the android market\n";

        steps[2] = 1;
        lifeAreas[2] = LifeArea.MONEY;

        questions[3] = "You go to a karaoke bar with some friends\n" +
                "\t1. I want it!\n";
        positiveConsequences[3] = "You sing your butt off and you shine like a diamond!\n";
        negativeConsequences[3] = "You sound like a screeching cat, get off stage!\n";
        steps[3] = 1;
        lifeAreas[3] = LifeArea.HAPPINESS;

        questions[4] = "You finally start hitting the gym\n" +
                "\t1. I want it!\n";
        positiveConsequences[4] = "Working out starts to pay off\n";
        negativeConsequences[4] = "You get an injury to your knee\n";
        steps[4] = 1;
        lifeAreas[4] = LifeArea.HAPPINESS;

        questions[5] = "You decide to learn Python in a night school\n" +
                "\t1. I want it!\n";
        positiveConsequences[5] = "Your amazing Phyton coding skills earn you a new highly paid job\n";
        negativeConsequences[5] = "You get robbed on your way to classes\n";
        steps[5] = 1;
        lifeAreas[5] = LifeArea.MONEY;

        questions[6] = "You buy a new mac\n" +
                "\t1. I want it!\n";
        positiveConsequences[6] = "You're coding so fast now you get a raise\n";
        negativeConsequences[6] = "Your mac explodes in your hands and the hospital bill is huge\n";
        steps[6] = 1;
        lifeAreas[6] = LifeArea.MONEY;

        questions[7] = "You make a fun game for your nephew in the weekend\n" +
                "\t1. I want it!\n";
        positiveConsequences[7] = "The game turns viral and everyone loves you\n";
        negativeConsequences[7] = "Your sister is mad because she thinks the kid spends to much time playing and not enough studying\n";
        steps[7] = 1;
        lifeAreas[7] = LifeArea.HAPPINESS;


    }

    @Override
    public void chooseAnswer(String answer, String username) {
        synchronized (queue) {
            String[] answerAndUsername = {answer, username};
            queue.offer(answerAndUsername);
        }

        synchronized (this) {
            if (queue.size() == server.getNumberOfPlayers()) {
                notifyAll();
            }
        }

    }

    private String getConsequence(int index, String sign) {
        return (sign.equals("+")) ? positiveConsequences[index] : negativeConsequences[index];
    }
}
