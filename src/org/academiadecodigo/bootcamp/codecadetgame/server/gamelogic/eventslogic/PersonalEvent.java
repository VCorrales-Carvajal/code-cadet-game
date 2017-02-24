package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by codecadet on 2/22/17.
 */
public class PersonalEvent implements Event {

    public static final int LENGTH_PERSONAL_EVENTS = 14;
    private final Server server;

    private String[] statements;
    private int[] steps;
    private LifeArea[] lifeArea;

    private int[] shuffledIndexes;
    private int counterIndexes = 0;

    public PersonalEvent(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        int index = shuffledIndexes[counterIndexes];

        String eventToDisplay = username + GameHelper.happenedToYou() + statements[index];

        server.sendMsgToAll(eventToDisplay);

        GameHelper.updateOnePlayerPosition(steps[index], username, server, lifeArea[index]);

        server.sendMsgToAll(getConsequenceString(username, index));

        counterIndexes++;
        if (counterIndexes == LENGTH_PERSONAL_EVENTS) {
            counterIndexes = 0;
        }

    }


    private void init() {
        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_PERSONAL_EVENTS);
        statements = new String[LENGTH_PERSONAL_EVENTS];
        steps = new int[LENGTH_PERSONAL_EVENTS];
        lifeArea = new LifeArea[LENGTH_PERSONAL_EVENTS];

        statements[0] = "You have been promoted";
        steps[0] = +1;
        lifeArea[0] = LifeArea.CAREER;

        statements[1] = "You were caught with the secretary and you got fired";
        steps[1] = -2;
        lifeArea[1] = LifeArea.CAREER;

        statements[2] = "You are fired because you missed work a couple of times";
        steps[2] = -1;
        lifeArea[2] = LifeArea.CAREER;

        statements[3] = "This is so great: your aunt has just died and left you lots of money!";
        steps[3] = +1;
        lifeArea[3] = LifeArea.MONEY;

        statements[4] = "You lost all your money with you drug habit";
        steps[4] = -1;
        lifeArea[4] = LifeArea.MONEY;

        statements[5] = "You spent a great evening with someone you met on Tinder";
        steps[5] = +1;
        lifeArea[5] = LifeArea.HAPPINESS;

        statements[6] = "You went to spend a week in Florence";
        steps[6] = +1;
        lifeArea[6] = LifeArea.HAPPINESS;

        statements[7] = "You have just fallen in love with a crocodile";
        steps[7] = +1;
        lifeArea[7] = LifeArea.HAPPINESS;

        statements[8] = "You just had the best awesome great delicious marvelous fantabulous meal in the world";
        steps[8] = +1;
        lifeArea[8] = LifeArea.HAPPINESS;

        statements[9] = "You delivered your project in time and then slept for 20h straight";
        steps[9] = +1;
        lifeArea[9] = LifeArea.HAPPINESS;

        statements[10] = "Someone broke your heart";
        steps[10] = -1;
        lifeArea[10] = LifeArea.HAPPINESS;

        statements[11] = "You are growing bald";
        steps[11] = -1;
        lifeArea[11] = LifeArea.HAPPINESS;

        statements[12] = "You just got an urinary infection";
        steps[12] = -1;
        lifeArea[12] = LifeArea.HAPPINESS;

        statements[13] = "You found your partner is cheating on you";
        steps[13] = -1;
        lifeArea[13] = LifeArea.HAPPINESS;

    }


    public String getConsequenceString(String username, int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;

        int step = steps[index];
        changeString = GameHelper.getStringGivenStep(step,
                Math.abs(step) + " step forward.", Math.abs(step) + " step back.");

        switch (lifeArea[index]) {

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
                        " happy!", " sad.");
                lifeAreaConsequence = "You are" + direction;
                break;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        String globalPositionString = lifeAreaConsequence + " " + changeString;

        return globalPositionString;
    }
}
