package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by codecadet on 2/22/17.
 */
public class PersonalEvent implements Event  {
    public static final int LENGTH_PERSONAL_EVENTS = 18;
    private final Server server;
    private String currentAnswer;

    private int[] shuffledIndexPersonal;
    private int counterShuffledIndexesPersonal = 0;

    public PersonalEvent(Server server) {
        this.server = server;
    }

    @Override
    public void process(String username) {
        //TODO ANTÓNIO: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))

        PlayerDispatcher p = server.getPlayerDispatcherTable().get(username);

        shuffledIndexPersonal = GameHelper.shuffleIndexArray(LENGTH_PERSONAL_EVENTS);

        int index;
        if (counterShuffledIndexesPersonal == LENGTH_PERSONAL_EVENTS) {
            counterShuffledIndexesPersonal = 0;
        }

        index = shuffledIndexPersonal[counterShuffledIndexesPersonal];

        String eventToDisplay = username + GameHelper.happenedToYou() + getPersonalEvents()[index];

        server.sendMsgToAll(eventToDisplay);

        p.setActive(true);
        p.setCurrentEvent(this);
        
        processAnswer(currentAnswer);
        
        counterShuffledIndexesPersonal++;

    }

    private void processAnswer(String currentAnswer) {
    }

    @Override
    public void setAnswer(String answer) {
        currentAnswer = answer;
    }


    public static String[] getPersonalEvents() {
        String[] pe = new String[LENGTH_PERSONAL_EVENTS];

        pe[0] = "You have been promoted";
        pe[1] = "You were caught with the secretary and you got fired";
        pe[2] = "You are fired because you missed work a couple of times";
        pe[3] = "This is so great: your aunt has just died and left you lots of money!";
        pe[4] = "You lost all your money with you drug habit";
        pe[5] = "You spent a great evening with someone you met on Tinder";
        pe[6] = "You went to spend a week in Florence";
        pe[7] = "You have just fallen in love with a crocodile";
        pe[8] = "You just had the best awesome great delicious marvelous fantabulous meal in the world";
        pe[9] = "You delivered your project in time and then slept for 20h straight";
        pe[10] = "Someone broke your heart";
        pe[11] = "You are growing bald";
        pe[12] = "You just got an urinary infection";
        pe[13] = "You found your partner is cheating on you";

        return pe;
    }

    public static int getStepsChangedPersonalEvents(int index) {
        int[] stepsPE = new int[LENGTH_PERSONAL_EVENTS];

        stepsPE[0] = +1;
        stepsPE[1] = -2;
        stepsPE[2] = -1;
        stepsPE[3] = +1;
        stepsPE[4] = -1;
        stepsPE[5] = +1;
        stepsPE[6] = +1;
        stepsPE[7] = +1;
        stepsPE[8] = +1;
        stepsPE[9] = +1;
        stepsPE[10] = -1;
        stepsPE[11] = -1;
        stepsPE[12] = -1;
        stepsPE[13] = -1;

        return stepsPE[index];
    }

    public static LifeArea getAreaChangedPersonalEvents(int index) {
        LifeArea[] lifeAreaPE = new LifeArea[LENGTH_PERSONAL_EVENTS];

        lifeAreaPE[0] = LifeArea.CAREER;
        lifeAreaPE[1] = LifeArea.CAREER;
        lifeAreaPE[2] = LifeArea.CAREER;
        lifeAreaPE[3] = LifeArea.MONEY;
        lifeAreaPE[4] = LifeArea.MONEY;
        lifeAreaPE[5] = LifeArea.HAPPINESS;
        lifeAreaPE[6] = LifeArea.HAPPINESS;
        lifeAreaPE[7] = LifeArea.HAPPINESS;
        lifeAreaPE[8] = LifeArea.HAPPINESS;
        lifeAreaPE[9] = LifeArea.HAPPINESS;
        lifeAreaPE[10] = LifeArea.HAPPINESS;
        lifeAreaPE[11] = LifeArea.HAPPINESS;
        lifeAreaPE[12] = LifeArea.HAPPINESS;
        lifeAreaPE[13] = LifeArea.HAPPINESS;

        return lifeAreaPE[index];
    }

    public static String getConsequencePersonalEvents(int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;

        int step = getStepsChangedPersonalEvents(index);
        changeString = getStringGivenStepPersonalEvents(step,
                Math.abs(step) + " step forward.", Math.abs(step) + " step back.");

        switch (getAreaChangedPersonalEvents(index)) {

            case CAREER:
                direction = getStringGivenStepPersonalEvents(step,
                        " moved forward ", " had a setback ");
                lifeAreaConsequence = "You" + direction + "in your career!";
                break;

            case MONEY:
                direction = getStringGivenStepPersonalEvents(step,
                        " earned ", " just lost ");
                lifeAreaConsequence = "You" + direction + "money!";
                break;

            case HAPPINESS:
                direction = getStringGivenStepPersonalEvents(step,
                        " happy!", " sad.");
                lifeAreaConsequence = "You are" + direction;
                break;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        return lifeAreaConsequence + " " + changeString;
    }

    private static String getStringGivenStepPersonalEvents(int step, String stringPositive, String stringNegative) {
        if (step > 0) {
            return stringPositive;
        } else {
            return stringNegative;
        }
    }
}
