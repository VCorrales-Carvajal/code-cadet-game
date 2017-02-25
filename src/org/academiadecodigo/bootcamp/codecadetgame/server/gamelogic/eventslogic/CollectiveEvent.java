package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

/**
 * Created by codecadet on 2/22/17.
 */
public class CollectiveEvent implements Event {

    public static final int LENGTH_COLLECTIVE_EVENTS = 11;
    private final EventType eventType = EventType.COLLECTIVE_EVENT;
    private final Server server;

    private String[] statements;
    private int[] steps;
    private LifeArea[] lifeArea;

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
        lifeArea = new LifeArea[LENGTH_COLLECTIVE_EVENTS];

        // Display selected statement
        String eventToDisplay = GameHelper.happenedToEveryOne() + statements[index];
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Update all players' position
        GameHelper.updatePlayersPositions(steps[index], server, lifeArea[index]);

        // Send message to all showing what happened
        server.sendMsgToAll(GameHelper.informLifeAreaAffected(username, steps[index], lifeArea[index], eventType));

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

        statements[7] = "A Sharknado destroys town";
        steps[7] = -1;
        lifeArea[7] = LifeArea.MONEY;

        statements[8] = "Meteorite crashes with earth and causes new ice age";
        steps[8] = -1;
        lifeArea[8] = LifeArea.HAPPINESS;

        statements[9] = "Zombie apocalypse";
        steps[9] = -1;
        lifeArea[9] = LifeArea.HAPPINESS;

        statements[10] = "The internet breaks down";
        steps[10] = -1;
        lifeArea[10] = LifeArea.HAPPINESS;
    }

}
