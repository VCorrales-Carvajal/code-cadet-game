package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;

/**
 * Created by codecadet on 2/18/17.
 */
public class Events {

    /**
     * String arrays:
     * questions
     * moneyTimeEvents
     * happinessEvents
     */

    public static final int LENGTH_QUESTIONS = 2;


    public static String firstGreeting() {

        return "You are on your last day of your bootcamp and to graduate you have to answer a question: ";

    }

    public static String questions() {

        return getQuestions()[ProbManager.chooseEqual(getQuestions().length)];

    }

    public static String[] getQuestions() {
        int numberOfQuestions = 2;
        String[] questions = new String[numberOfQuestions];

        questions[0] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2.Bytecode\n" +
                "\t3.Morse code\n" +
                "\t4.Usain Bolt\n";

        return questions;
    }








}
