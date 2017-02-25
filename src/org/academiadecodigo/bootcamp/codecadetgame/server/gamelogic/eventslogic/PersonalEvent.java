package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

/**
 * Created by codecadet on 2/22/17.
 */
public class PersonalEvent implements Event {

    public static final int LENGTH_PERSONAL_EVENTS = 14;

    private final Server server;

    private String[] statements;
    private int[] steps;
    private LifeAreas[] lifeAreas;

    private int[] shuffledIndexes;
    private int counterIndex = 0;

    public PersonalEvent(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        // Display selected statement
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = username + GameHelper.happenedToYou() + statements[index];

        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Update player's position
        GameHelper.updateOnePlayerPosition(steps[index], username, server, lifeAreas[index]);

        // Send message to all showing what happened
        server.sendMsgToAll(getConsequenceString(username, index));

        // Increase counter
        counterIndex++;
        if (counterIndex == LENGTH_PERSONAL_EVENTS) {
            counterIndex = 0;
        }

    }


    private void init() {

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_PERSONAL_EVENTS);

        statements = new String[LENGTH_PERSONAL_EVENTS];
        steps = new int[LENGTH_PERSONAL_EVENTS];
        lifeAreas = new LifeAreas[LENGTH_PERSONAL_EVENTS];

        statements[0] = "You have been promoted";
        steps[0] = +1;
        lifeAreas[0] = LifeAreas.CAREER;

        statements[1] = "You were caught with the secretary and you got fired";
        steps[1] = -2;
        lifeAreas[1] = LifeAreas.CAREER;

        statements[2] = "You are fired because you missed work a couple of times";
        steps[2] = -1;
        lifeAreas[2] = LifeAreas.CAREER;

        statements[3] = "This is so great: your aunt has just died and left you lots of money!";
        steps[3] = +1;
        lifeAreas[3] = LifeAreas.MONEY;

        statements[4] = "You lost all your money with you drug habit";
        steps[4] = -1;
        lifeAreas[4] = LifeAreas.MONEY;

        statements[5] = "You spent a great evening with someone you met on Tinder";
        steps[5] = +1;
        lifeAreas[5] = LifeAreas.HAPPINESS;

        statements[6] = "You went to spend a week in Florence";
        steps[6] = +1;
        lifeAreas[6] = LifeAreas.HAPPINESS;

        statements[7] = "You have just fallen in love with a crocodile";
        steps[7] = +1;
        lifeAreas[7] = LifeAreas.HAPPINESS;

        statements[8] = "You just had the best awesome great delicious marvelous fantabulous meal in the world";
        steps[8] = +1;
        lifeAreas[8] = LifeAreas.HAPPINESS;

        statements[9] = "You delivered your project in time and then slept for 20h straight";
        steps[9] = +1;
        lifeAreas[9] = LifeAreas.HAPPINESS;

        statements[10] = "Someone broke your heart";
        steps[10] = -1;
        lifeAreas[10] = LifeAreas.HAPPINESS;

        statements[11] = "You are growing bald";
        steps[11] = -1;
        lifeAreas[11] = LifeAreas.HAPPINESS;

        statements[12] = "You just got an urinary infection";
        steps[12] = -1;
        lifeAreas[12] = LifeAreas.HAPPINESS;

        statements[13] = "You found your partner is cheating on you";
        steps[13] = -1;
        lifeAreas[13] = LifeAreas.HAPPINESS;

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

            case CAREER:
                direction = GameHelper.getStringGivenStep(step,
                        " moved forward ", " had a setback ");
                lifeAreaConsequence = "You" + direction + "in your career!";
                break;

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
}
