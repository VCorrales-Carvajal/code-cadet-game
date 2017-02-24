package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by codecadet on 2/22/17.
 */
public class PersonalEvent implements Event  {
    public static final int LENGTH_PERSONAL_EVENTS = 14;
    private final Server server;
    private String currentAnswer;

    private String[] personalEvents;
    private int[] stepsPersonalEvents;
    private LifeArea[] lifeAreaPersonalEvents;

    private int[] shuffledIndexPersonal;
    private int counterShuffledIndexesPersonal = 0;

    public PersonalEvent(Server server) {
        this.server = server;
        init();
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

//        p.setActive(true);
//        p.setCurrentEvent(this);
        
        processAnswer(currentAnswer);
        
        counterShuffledIndexesPersonal++;

    }

    private void processAnswer(String currentAnswer) {
    }

    @Override
    public void setAnswer(String answer) {
        currentAnswer = answer;
    }

    private void init() {
        personalEvents = new String[LENGTH_PERSONAL_EVENTS];
        stepsPersonalEvents = new int[LENGTH_PERSONAL_EVENTS];
        lifeAreaPersonalEvents = new LifeArea[LENGTH_PERSONAL_EVENTS];

        personalEvents[0] = "You have been promoted";
        stepsPersonalEvents[0] = +1;
        lifeAreaPersonalEvents[0] = LifeArea.CAREER;

        personalEvents[1] = "You were caught with the secretary and you got fired";
        stepsPersonalEvents[1] = -2;
        lifeAreaPersonalEvents[1] = LifeArea.CAREER;

        personalEvents[2] = "You are fired because you missed work a couple of times";
        stepsPersonalEvents[2] = -1;
        lifeAreaPersonalEvents[2] = LifeArea.CAREER;

        personalEvents[3] = "This is so great: your aunt has just died and left you lots of money!";
        stepsPersonalEvents[3] = +1;
        lifeAreaPersonalEvents[3] = LifeArea.MONEY;

        personalEvents[4] = "You lost all your money with you drug habit";
        stepsPersonalEvents[4] = -1;
        lifeAreaPersonalEvents[4] = LifeArea.MONEY;

        personalEvents[5] = "You spent a great evening with someone you met on Tinder";
        stepsPersonalEvents[5] = +1;
        lifeAreaPersonalEvents[5] = LifeArea.HAPPINESS;

        personalEvents[6] = "You went to spend a week in Florence";
        stepsPersonalEvents[6] = +1;
        lifeAreaPersonalEvents[6] = LifeArea.HAPPINESS;

        personalEvents[7] = "You have just fallen in love with a crocodile";
        stepsPersonalEvents[7] = +1;
        lifeAreaPersonalEvents[7] = LifeArea.HAPPINESS;

        personalEvents[8] = "You just had the best awesome great delicious marvelous fantabulous meal in the world";
        stepsPersonalEvents[8] = +1;
        lifeAreaPersonalEvents[8] = LifeArea.HAPPINESS;

        personalEvents[9] = "You delivered your project in time and then slept for 20h straight";
        stepsPersonalEvents[9] = +1;
        lifeAreaPersonalEvents[9] = LifeArea.HAPPINESS;

        personalEvents[10] = "Someone broke your heart";
        stepsPersonalEvents[10] = -1;
        lifeAreaPersonalEvents[10] = LifeArea.HAPPINESS;

        personalEvents[11] = "You are growing bald";
        stepsPersonalEvents[11] = -1;
        lifeAreaPersonalEvents[11] = LifeArea.HAPPINESS;

        personalEvents[12] = "You just got an urinary infection";
        stepsPersonalEvents[12] = -1;
        lifeAreaPersonalEvents[12] = LifeArea.HAPPINESS;

        personalEvents[13] = "You found your partner is cheating on you";
        stepsPersonalEvents[13] = -1;
        lifeAreaPersonalEvents[13] = LifeArea.HAPPINESS;

    }

    public static String[] getPersonalEvents() {
        String[] personalEvents = new String[LENGTH_PERSONAL_EVENTS];

        personalEvents[0] = "You have been promoted";
        personalEvents[1] = "You were caught with the secretary and you got fired";
        personalEvents[2] = "You are fired because you missed work a couple of times";
        personalEvents[3] = "This is so great: your aunt has just died and left you lots of money!";
        personalEvents[4] = "You lost all your money with you drug habit";
        personalEvents[5] = "You spent a great evening with someone you met on Tinder";
        personalEvents[6] = "You went to spend a week in Florence";
        personalEvents[7] = "You have just fallen in love with a crocodile";
        personalEvents[8] = "You just had the best awesome great delicious marvelous fantabulous meal in the world";
        personalEvents[9] = "You delivered your project in time and then slept for 20h straight";
        personalEvents[10] = "Someone broke your heart";
        personalEvents[11] = "You are growing bald";
        personalEvents[12] = "You just got an urinary infection";
        personalEvents[13] = "You found your partner is cheating on you";

        return personalEvents;
    }

    public static int getStepsChangedPersonalEvents(int index) {
        int[] stepsPersonalEvents = new int[LENGTH_PERSONAL_EVENTS];

        stepsPersonalEvents[0] = +1;
        stepsPersonalEvents[1] = -2;
        stepsPersonalEvents[2] = -1;
        stepsPersonalEvents[3] = +1;
        stepsPersonalEvents[4] = -1;
        stepsPersonalEvents[5] = +1;
        stepsPersonalEvents[6] = +1;
        stepsPersonalEvents[7] = +1;
        stepsPersonalEvents[8] = +1;
        stepsPersonalEvents[9] = +1;
        stepsPersonalEvents[10] = -1;
        stepsPersonalEvents[11] = -1;
        stepsPersonalEvents[12] = -1;
        stepsPersonalEvents[13] = -1;

        return stepsPersonalEvents[index];
    }

    public static LifeArea getAreaChangedPersonalEvents(int index) {
        LifeArea[] lifeAreaPersonalEvents = new LifeArea[LENGTH_PERSONAL_EVENTS];

        lifeAreaPersonalEvents[0] = LifeArea.CAREER;
        lifeAreaPersonalEvents[1] = LifeArea.CAREER;
        lifeAreaPersonalEvents[2] = LifeArea.CAREER;
        lifeAreaPersonalEvents[3] = LifeArea.MONEY;
        lifeAreaPersonalEvents[4] = LifeArea.MONEY;
        lifeAreaPersonalEvents[5] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[6] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[7] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[8] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[9] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[10] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[11] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[12] = LifeArea.HAPPINESS;
        lifeAreaPersonalEvents[13] = LifeArea.HAPPINESS;

        return lifeAreaPersonalEvents[index];
    }

    public String getConsequencePersonalEvents(int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;

        int step = stepsPersonalEvents[index];
        changeString = GameHelper.getStringGivenStep(step,
                Math.abs(step) + " step forward.", Math.abs(step) + " step back.");

        switch (lifeAreaPersonalEvents[index]) {

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

        return lifeAreaConsequence + " " + changeString;
    }
}
