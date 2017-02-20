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

    public static final int LENGTH_QUESTIONS = 2;
    public static final int LENGTH_COLLECTIVE_EVENTS = 7;

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

    public static String[] getCollectiveEvents() {
        String[] ce = new String[LENGTH_COLLECTIVE_EVENTS];

        ce[0] = "Brexit causes all British tech companies to move to Portugal and everyone gets a better job";
        ce[1] = "Artificial intelligence takes away everyone’s job";
        ce[2] = "All tech companies moved to Romania and everyone loses the job";
        ce[3] = "Lower taxes for all";
        ce[4] = "More taxes due to the new war with North Korea";
        ce[5] = "Portugal wins the World Cup";
        ce[6] = "Donald Trump has died";

        return ce;
    }

    public static int getStepsChangedCollectiveEvents(int index) {

        int[] stepsCE = new int[LENGTH_COLLECTIVE_EVENTS];
        stepsCE[0] = 1;
        stepsCE[1] = -1;
        stepsCE[2] = -1;
        stepsCE[3] = +1;
        stepsCE[4] = -1;
        stepsCE[5] = +1;
        stepsCE[6] = +1;

        return stepsCE[index];
    }

    public static LifeArea getAreaChangedCollectiveEvents(int index) {

        LifeArea[] stepsCE = new LifeArea[LENGTH_COLLECTIVE_EVENTS];
        stepsCE[0] = LifeArea.CAREER;
        stepsCE[1] = LifeArea.CAREER;
        stepsCE[2] = LifeArea.CAREER;
        stepsCE[3] = LifeArea.MONEY;
        stepsCE[4] = LifeArea.MONEY;
        stepsCE[5] = LifeArea.HAPPINESS;
        stepsCE[6] = LifeArea.HAPPINESS;

        return stepsCE[index];
    }

    public static String getConsequenceCollectiveEvents(int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;


        int step = getStepsChangedCollectiveEvents(index);
        changeString = getStringGivenStepCollectiveEvents(step,
                Math.abs(step) + " step forward.", Math.abs(step) + " step back.");

        switch (getAreaChangedCollectiveEvents(index)) {

            case CAREER:
                direction = getStringGivenStepCollectiveEvents(step,
                        " is moving forward ", " has a setback ");
                lifeAreaConsequence = "Everyone" + direction + "in their career!";
                break;

            case MONEY:
                direction = getStringGivenStepCollectiveEvents(step,
                        " earns ", " loses ");
                lifeAreaConsequence = "Everyone" + direction + "money!";
                break;

            case HAPPINESS:
                direction = getStringGivenStepCollectiveEvents(step, " happy!", " sad.");
                lifeAreaConsequence = "Everyone is" + direction;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        return lifeAreaConsequence + " " + changeString;
    }


    private static String getStringGivenStepCollectiveEvents(int step, String stringPositive, String stringNegative) {
        if (step > 0) {
            return stringPositive;
        } else {
            return stringNegative;
        }
    }

}
