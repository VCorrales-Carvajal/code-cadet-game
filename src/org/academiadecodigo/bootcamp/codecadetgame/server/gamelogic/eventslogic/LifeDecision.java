package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by codecadet on 2/22/17.
 */
public class LifeDecision implements Event {
    public final static int LIFE_DECISIONS_LENGTH = 2;

    private final Server server;
    private String[] statements;
    private String[] positiveConsequences;
    private String[] negativeConsequences;
    private int[] shuffledIndexes;

    public LifeDecision(Server server) {
        this.server = server;
    }

    @Override
    public void process() {
        init();
        //

        //TODO VERO: Verifies event pe and asks respective Class to resolve
        // (send message to players, check players answers/results and update players positions))
    }

    private void init() {
        shuffledIndexes = GameHelper.shuffleIndexArray(LIFE_DECISIONS_LENGTH);
    }
}
