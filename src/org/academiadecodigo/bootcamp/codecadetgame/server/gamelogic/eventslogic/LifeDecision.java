package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;


/**
 * Created by codecadet on 2/22/17.
 */
//Personal choosable
public class LifeDecision implements ChoosableEvent {

    public final static int LENGTH_LIFE_DECISIONS = 2;
    public final static int NUMBER_OF_OPTIONS_SHOWN = 3;

    private final Server server;
    private String currentAnswer;
    private String[] statements;
    private String[] positiveConsequences;
    private String[] negativeConsequences;
    private int[] steps;
    private LifeAreas[] lifeAreas;
    private int[] shuffledIndexes;
    private int lastIndexUsed = - 1;

    private int[] currentIndexes;

    public LifeDecision(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        PlayerDispatcher p = server.getPlayerDispatcherTable().get(username);

        // Display selected statement
        String eventToDisplay = GameHelper.lifeDecision(username) + getStatement();
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Listen to the answer of this player
        p.setActive(true);
        p.setCurrentEvent(this);

        // Process player's answer
        processAnswer(currentAnswer);

    }

    private void processAnswer(String currentAnswer) {
    }


    private String getStatement() {

        int start = shuffledIndexes[lastIndexUsed + 1];
        int end = shuffledIndexes[start + NUMBER_OF_OPTIONS_SHOWN - 1];

        int[] statementIndices = new int[NUMBER_OF_OPTIONS_SHOWN];
        String statement = "";
        int j = 0;
        for (int i = start; i <= end; i++) {
            statementIndices[j++] = i;
            statement = statements[i];
        }

        return statement;

    }


    private void init() {
        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_LIFE_DECISIONS);

        statements[0] = "Spend your evenings at a workshop learning more about programming";
        positiveConsequences[0] = "You get promoted for showing good results as a consequence of the new stuff you learned";
        steps[0] = 1;
        negativeConsequences[0] = "With less hours of sleep, your productivity went down and you got demoted";
        lifeAreas[0] = LifeAreas.CAREER;

    }

    @Override
    public void chooseAnswer(String answer) {
        currentAnswer = answer;
    }
}
