package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.MsgHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventString;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */
public class Game {

    Server server;

    public Game(Server server) {

        this.server = server;
    }

    public void start() {

        server.sendMsgToAll(Events.firstGreeting());

    }

    //TODO: When player chooses an option that is not available, assume it's wrong
    private void turnCycle() {


        EventType eventType = EventType.choose();

        displayEvent(eventType);

        if (eventType.isChoosable()) {
            getPlayerAnswer();
        }

        displayConsequence();

        MsgHelper.displayPlayersPosition();

        if (Math.random() < Constants.PROB_COW_WISDOM_QUOTE) {
            MsgHelper.displayCowWisdomQuote();
        }


        /*String eventToDisplay = null;

        switch (eventType) {
            case COLLECTIVE_CHOOSABLE:
                eventToDisplay = collectiveChoosable();
                break;
            case COLLECTIVE_NON_CHOOSABLE:
                break;
            case PERSONAL_CHOOSABLE:
                break;
            case PERSONAL_NON_CHOOSABLE:

        }
        return eventToDisplay;*/

    }

    private void getPlayerAnswer() {
        throw new UnsupportedOperationException();
    }

    private void displayEvent(EventType eventType) {
        throw new UnsupportedOperationException();
    }

    private void displayConsequence() {
        throw new UnsupportedOperationException();
    }

    private String collectiveChoosable() {
        EventString cc = EventString.values()
                [ProbManager.chooseEqual(EventString.values().length)];

        String eventToDisplay = null;

        switch (cc) {
            case QUESTION:
                eventToDisplay = Events.questions();
                break;
            case TIME_EVENT:


        }

        return eventToDisplay;

    }


}
