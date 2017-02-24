package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;


/**
 * Created by codecadet on 2/22/17.
 */
//Personal choosable
public class LifeDecision implements ChoosableEvent {

    public final static int LIFE_DECISIONS_LENGTH = 2;
    public final static int NUMBER_OF_OPTIONS_SHOWN = 3;

    private final Server server;
    private String currentAnswer;
    private String[] statements;
    private String[] positiveConsequences;
    private String[] negativeConsequences;
    private LifeArea[] lifeAreas;
    private int[] shuffledIndexes;
    private int lastIndexUsed = - 1;

    private int[] currentIndices;

    public LifeDecision(Server server) {
        this.server = server;
        init();
    }


    //TODO VERO: Verifies event pe and asks respective Class to resolve
    // (send message to players, check players answers/results and update players positions))
    @Override
    public void process(String username) {

        PlayerDispatcher p = server.getPlayerDispatcherTable().get(username);

        int index = 0;
        String eventToDisplay = null;

        server.sendMsgToAll(eventToDisplay);

        p.setActive(true);
        p.setCurrentEvent(this);

        processAnswer(currentAnswer);

    }

    private void processAnswer(String currentAnswer) {
    }

    private String getStatement() {
        shuffledIndexes = GameHelper.shuffleIndexArray(LIFE_DECISIONS_LENGTH);
        int start = shuffledIndexes[lastIndexUsed + 1];
        int end = shuffledIndexes[start + NUMBER_OF_OPTIONS_SHOWN - 1];

        int[] statementIndices = new int[NUMBER_OF_OPTIONS_SHOWN];
        String statement = "";
        int j = 0;
        for (int i = start; i <= end; i++) {
            statementIndices[j++] = i;
            statement = getLifeDecisions()[i];
        }
        return statement;
    }

    public String[] getLifeDecisions() {
        return null;
    }

    private void init() {
        statements[0] = "Spend your evenings at a workshop learning more about programming";
        positiveConsequences[0] = "You get promoted for showing good results as a consequence of the new stuff you learned";
        negativeConsequences[0] = "You get promoted for showing good results as a consequence of the new stuff you learned";

    }

    @Override
    public void chooseAnswer(String answer) {
        currentAnswer = answer;

    }
}
