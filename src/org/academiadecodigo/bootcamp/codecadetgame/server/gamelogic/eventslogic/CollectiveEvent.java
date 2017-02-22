package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Events;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;

/**
 * Created by codecadet on 2/22/17.
 */
public class CollectiveEvent implements Event {
    public static final int LENGTH_COLLECTIVE_EVENTS = 7;
    private final Server server;

    private int[] shuffledIndexesCollective;
    private int counterShuffledIndexesCollective = 0;

    public CollectiveEvent(Server server) {
        this.server = server;
    }


    @Override
    public void process() {
        //TODO BONI: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
        shuffledIndexesCollective = GameHelper.shuffleIndex(LENGTH_COLLECTIVE_EVENTS);

        int index;
        if (counterShuffledIndexesCollective == LENGTH_COLLECTIVE_EVENTS) {
            counterShuffledIndexesCollective = 0;
        }
        index = shuffledIndexesCollective[counterShuffledIndexesCollective];
        String eventToDisplay = getCollectiveEvents()[index];
        counterShuffledIndexesCollective++;



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
