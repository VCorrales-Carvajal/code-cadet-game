package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;


/**
 * Created by codecadet on 2/22/17.
 */
//Personal choosable
public class LifeDecision implements ChoosableEvent {

    public final static int LENGTH_LIFE_DECISIONS = 13;
    public final static int NUMBER_OF_OPTIONS_SHOWN = 3;
    private final double probabilityPositive = 0.8;
    private final EventType eventType = EventType.LIFE_DECISION;

    private final Server server;

    private String[] statements;
    private String[] positiveConsequences;
    private String[] negativeConsequences;
    private int[] steps;
    private LifeArea[] lifeAreas;

    private int[] shuffledIndexes;
    private int counterIndex = 0;
    private int[] currentIndexes;

    private String currentAnswer;

    public LifeDecision(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        // Display selected statement
        String eventToDisplay = GameHelper.displayEventType(username, eventType) + getStatement();
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Listen to the answer of this player
        PlayerDispatcher p = server.getPlayerDispatcherTable().get(username);
        p.setActive(true);
        p.setCurrentEvent(this);

        // Process player's answer
        processAnswer(username);

    }

    private void processAnswer(String username) {

        synchronized (this) {
            while (currentAnswer == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //Thread.interrupt called, no handling needed
                }
            }
        }

        int index = -1;
        if (currentAnswer != null) {
            for (int i = 0; i < NUMBER_OF_OPTIONS_SHOWN; i++) {
                if (currentAnswer.equals(Integer.toString(i + 1))) {
                    index = currentIndexes[i];
                    break;
                }
            }

        }
        currentAnswer = null;

        if (index != -1) {
            // Send message to all showing what happened
            String sign = (Math.random() < probabilityPositive) ? "+" : "-";
            int step = (sign.equals("+")) ? steps[index] : -steps[index];

            String consequence = getConsequence(index, sign);
            System.out.println("Consequence is " + consequence);
            server.sendMsgToAll(MsgFormatter.gameMsg(consequence));
            server.sendMsgToAll(GameHelper.informLifeAreaAffected(username, step, lifeAreas[index], eventType));

            // Update player's position
            GameHelper.updatePlayersPositions(step, username, server, lifeAreas[index]);

        } else {

            server.sendMsgToAll(MsgFormatter.gameMsg(GameHelper.invalidAnswer()));

        }

    }


    private String getStatement() {
        String statement = "";
        for (int i = 0; i < NUMBER_OF_OPTIONS_SHOWN; i++) {
            statement = statement + "\t" + (i + 1) + ". " + statements[shuffledIndexes[counterIndex]] + "\n";
            currentIndexes[i] = shuffledIndexes[counterIndex];

            counterIndex++;
            if (counterIndex == statements.length) {
                counterIndex = 0;
                shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_LIFE_DECISIONS);
            }
        }

        return statement;
    }


    private void init() {
        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_LIFE_DECISIONS);

        currentIndexes = new int[NUMBER_OF_OPTIONS_SHOWN];

        statements = new String[LENGTH_LIFE_DECISIONS];
        positiveConsequences = new String[LENGTH_LIFE_DECISIONS];
        negativeConsequences = new String[LENGTH_LIFE_DECISIONS];
        steps = new int[LENGTH_LIFE_DECISIONS];
        lifeAreas = new LifeArea[LENGTH_LIFE_DECISIONS];

        statements[0] = "Spend your evenings at a workshop learning more about programming";
        positiveConsequences[0] = "You get promoted for showing good results as a consequence of the new stuff you learned";
        negativeConsequences[0] = "With less hours of sleep your productivity went down and you got demoted";
        steps[0] = 1;
        lifeAreas[0] = LifeArea.CAREER;

        statements[1] = "Party all night long and drink like Keith Richards";
        positiveConsequences[1] = "You make some new clients";
        negativeConsequences[1] = "You don’t manage to wake up and fail to deliver an important project and you get fired";
        steps[1] = 1;
        lifeAreas[1] = LifeArea.CAREER;


        statements[2] = "Offer a meal to a homeless person";
        positiveConsequences[2] = "It turns out the homeless man is a millionaire and gives you lots of money";
        negativeConsequences[2] = "You lose money";
        steps[2] = 1;
        lifeAreas[2] = LifeArea.MONEY;

        statements[3] = "Buy facebook stocks";
        positiveConsequences[3] = "As expected, facebook continues growing rapidly";
        negativeConsequences[3] = "Facebook collapses and you lose all the money";
        steps[3] = 1;
        lifeAreas[3] = LifeArea.MONEY;

        statements[4] = "Go on a blind date";
        positiveConsequences[4] = "You found the love of your life";
        negativeConsequences[4] = "You lose a kidney";
        steps[4] = 1;
        lifeAreas[4] = LifeArea.HAPPINESS;

        statements[5] = "Donate blood";
        positiveConsequences[5] = "You save a person’s life";
        negativeConsequences[5] = "The national health system is shitty and you caught an infection from the needle";
        steps[5] = 1;
        lifeAreas[5] = LifeArea.HAPPINESS;

        statements[6] = "Give a present to your significant other";
        positiveConsequences[6] = "You spend a romantic evening";
        negativeConsequences[6] = "You bought a cake and your partner, who’s allergic, accuses you of attempted murder";
        steps[6] = 1;
        lifeAreas[6] = LifeArea.HAPPINESS;

        statements[7] = "You get yourself a nice little puppy";
        positiveConsequences[7] = "A girl starts talking to you in the park because of your wonderfull puppy; she becomes your girlfriend";
        negativeConsequences[7] = "Turns out, the puppy is a demon, bytes your foot and you end up in the hospital";
        steps[7] = 1;
        lifeAreas[7] = LifeArea.HAPPINESS;

        statements[8] = "You buy a bike, because you wanna be healthy";
        positiveConsequences[8] = "You're so healthy you work more and you earn lots of money";
        negativeConsequences[8] = "You're bycicle is stolen and you buy another one - and spend a lot in a new insurance";
        steps[8] = 1;
        lifeAreas[8] = LifeArea.MONEY;

        statements[9] = "You donate money for your local zoo";
        positiveConsequences[9] = "Your boss admires your empathy. He makes you new product leader out of admiration";
        negativeConsequences[9] = "Your boss is an animal lover that hates zoos. He fires you";
        steps[9] = 1;
        lifeAreas[9] = LifeArea.CAREER;

        statements[10] = "You fall in love with footabll and spend each second of free time watching it";
        positiveConsequences[10] = "You make an app to understand player's movements - and you get rich";
        negativeConsequences[10] = "Boss catches you watching illegal streams at work and substitutes you for a machine (a prettier one too)";
        steps[10] = 1;
        lifeAreas[10] = LifeArea.CAREER;

        statements[11] = "You give some bread to a nice little duck in a park";
        positiveConsequences[11] = "The duck starts choking. You give him mouth-to-mouth resuscitation. The duck spits something that was chocking. It's a lot of 500 euro bills that he had swallowed. No one's looking, so you keep the money.";
        negativeConsequences[11] = "The duck chokes on the bread and dies. Your wife asks for divorce on the basis of animal cruelty. You spend a lot of money in that divorce.";
        steps[11] = 1;
        lifeAreas[11] = LifeArea.MONEY;

        statements[12] = "You help an old lady cross the street";
        positiveConsequences[12] = "She smiles at you and you feel a warmth inside";
        negativeConsequences[12] = "The moment she crosses the street a piano falls on her head and she dies. You feel awfull.";
        steps[12] = 1;
        lifeAreas[12] = LifeArea.HAPPINESS;
    }

    @Override
    public void chooseAnswer(String answer, String username) {
        synchronized (this) {
            currentAnswer = answer;
            notifyAll();
        }
    }

    private String getConsequence(int index, String sign) {
        return (sign.equals("+")) ? positiveConsequences[index] : negativeConsequences[index];
    }
}
