package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;

/**
 * Created by codecadet on 2/22/17.
 */
public class CollectiveEvent implements Event {
    public static final int LENGTH_COLLECTIVE_EVENTS = 7;
    private final Server server;

    private String[] statements = new String[LENGTH_COLLECTIVE_EVENTS];
    private int[] steps = new int[LENGTH_COLLECTIVE_EVENTS];
    private LifeArea[] lifeArea = new LifeArea[LENGTH_COLLECTIVE_EVENTS];

    private int[] shuffledIndexes;
    private int counterIndexes = 0;

    public CollectiveEvent(Server server) {
        this.server = server;
    }


    @Override
    public void process(String username) {

        int index = shuffledIndexes[counterIndexes];

        String eventToDisplay = username + GameHelper.happenedToYou() + statements[index];

        server.sendMsgToAll(eventToDisplay);

        GameHelper.updateOnePlayerPosition(steps[index], username, server);

        server.sendMsgToAll(getConsequenceCollectiveEvents(index));

        counterIndexes++;
        if (counterIndexes == LENGTH_COLLECTIVE_EVENTS) {
            counterIndexes = 0;
        }
    }

    private void processAnswer() {
    }


    private void init() {

        statements[0] = "Brexit causes all British tech companies to move to Portugal and everyone gets a better job";
        steps[0] = +1;
        lifeArea[0] = LifeArea.CAREER;

        statements[1] = "Artificial intelligence takes away everyone’s job";
        steps[1] = -1;
        lifeArea[1] = LifeArea.CAREER;

        statements[2] = "All tech companies moved to Romania and everyone loses the job";
        steps[2] = -1;
        lifeArea[2] = LifeArea.CAREER;

        statements[3] = "Lower taxes for all";
        steps[3] = +1;
        lifeArea[3] = LifeArea.MONEY;

        statements[4] = "More taxes due to the new war with North Korea";
        steps[4] = -1;
        lifeArea[4] = LifeArea.MONEY;

        statements[5] = "Portugal wins the World Cup";
        steps[5] = +1;
        lifeArea[5] = LifeArea.HAPPINESS;

        statements[6] = "Donald Trump has died";
        steps[6] = +1;
        lifeArea[6] = LifeArea.HAPPINESS;


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

        LifeArea[] lifeAreaCE = new LifeArea[LENGTH_COLLECTIVE_EVENTS];
        lifeAreaCE[0] = LifeArea.CAREER;
        lifeAreaCE[1] = LifeArea.CAREER;
        lifeAreaCE[2] = LifeArea.CAREER;
        lifeAreaCE[3] = LifeArea.MONEY;
        lifeAreaCE[4] = LifeArea.MONEY;
        lifeAreaCE[5] = LifeArea.HAPPINESS;
        lifeAreaCE[6] = LifeArea.HAPPINESS;

        return lifeAreaCE[index];
    }

    public static String getConsequenceCollectiveEvents(int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;


        int step = getStepsChangedCollectiveEvents(index);
        changeString = GameHelper.getStringGivenStep(step,
                Math.abs(step) + " step forward.", Math.abs(step) + " step back.");

        switch (getAreaChangedCollectiveEvents(index)) {

            case CAREER:
                direction = GameHelper.getStringGivenStep(step,
                        " is moving forward ", " has a setback ");
                lifeAreaConsequence = "Everyone" + direction + "in their career!";
                break;

            case MONEY:
                direction = GameHelper.getStringGivenStep(step,
                        " earns ", " loses ");
                lifeAreaConsequence = "Everyone" + direction + "money!";
                break;

            case HAPPINESS:
                direction = GameHelper.getStringGivenStep(step, " happy!", " sad.");
                lifeAreaConsequence = "Everyone is" + direction;
                break;

            default:
                lifeAreaConsequence = "Something is WRONG!!";

        }

        return lifeAreaConsequence + " " + changeString;
    }

}
