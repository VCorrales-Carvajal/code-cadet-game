package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

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


    public static String questions() {

        return getQuestions()[ProbManager.chooseEqual(getQuestions().length)];

    }

    private static String[] getQuestions() {
        int numberOfQuestions = 2;
        String[] questions = new String[numberOfQuestions];

        questions[0] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2.Bytecode\n" +
                "\t3.Morse code\n" +
                "\t4.Usain Bolt\n";

        return questions;
    }


    /**
     *

    CAREER
            ( + ) “Spend your evenings improving your skills in a workshop”
            ( - ) “You party all night long and drink like Keith Richards” → “You don’t manage to wake up and fail to deliver an important project and you get fired”
    MONEY
            ( + ) “Offer a meal to a homeless person” → “It turns out the homeless man is a millionaire and gives you lots of money”
            ( - ) “Buy facebook stocks” → “But facebook collapses and you lose all the money”
    HAPPINESS
            ( * ) “Go on a blind date”
            → ( + ) “You found the love of your life”
    */

}
