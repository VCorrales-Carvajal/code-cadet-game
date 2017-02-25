package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

/**
 * Created by codecadet on 2/22/17.
 */
public class CollectiveEvent implements Event {

    private final EventType eventType = EventType.COLLECTIVE_EVENT;
    public static final int LENGTH_COLLECTIVE_EVENTS = 7;
    private final Server server;

    private String[] statements;
    private int[] steps;
    private LifeAreas[] lifeArea;

    private int[] shuffledIndex;
    private int counterIndex = 0;

    public CollectiveEvent(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        int index = shuffledIndex[counterIndex];
        statements = new String[LENGTH_COLLECTIVE_EVENTS];
        steps = new int[LENGTH_COLLECTIVE_EVENTS];
        lifeArea = new LifeAreas[LENGTH_COLLECTIVE_EVENTS];

        // Display selected statement
        String eventToDisplay = GameHelper.happenedToEveryOne() + statements[index];
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Update all players' position
        GameHelper.updatePlayersPositions(steps[index], server, lifeArea[index]);

        // Send message to all showing what happened
        server.sendMsgToAll(getConsequenceString(index));

        // Increase counter
        counterIndex++;
        if (counterIndex == LENGTH_COLLECTIVE_EVENTS) {
            counterIndex = 0;
        }
    }


    private void init() {

        shuffledIndex = GameHelper.shuffleIndexArray(LENGTH_COLLECTIVE_EVENTS);



        statements[0] = "Brexit causes all British tech companies to move to Portugal and everyone gets a better job";
        steps[0] = +1;
        lifeArea[0] = LifeAreas.CAREER;

        statements[1] = "Artificial intelligence takes away everyone’s job";
        steps[1] = -1;
        lifeArea[1] = LifeAreas.CAREER;

        statements[2] = "All tech companies moved to Romania and everyone loses the job";
        steps[2] = -1;
        lifeArea[2] = LifeAreas.CAREER;

        statements[3] = "Lower taxes for all";
        steps[3] = +1;
        lifeArea[3] = LifeAreas.MONEY;

        statements[4] = "More taxes due to the new war with North Korea";
        steps[4] = -1;
        lifeArea[4] = LifeAreas.MONEY;

        statements[5] = "Portugal wins the World Cup";
        steps[5] = +1;
        lifeArea[5] = LifeAreas.HAPPINESS;

        statements[6] = "Donald Trump has died";
        steps[6] = +1;
        lifeArea[6] = LifeAreas.HAPPINESS;

    }


    public String getConsequenceString(int index) {

        String lifeAreaConsequence;
        String changeString;
        String direction;


        int step = steps[index];
        String stepString = (step != 0) ? " steps" : " step";

        switch (lifeArea[index]) {

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

        changeString = GameHelper.getStringGivenStep(step,
                Math.abs(step) + " steps forward.", Math.abs(step) + stepString + " back.");

        return lifeAreaConsequence + " " + changeString;

    }

}
