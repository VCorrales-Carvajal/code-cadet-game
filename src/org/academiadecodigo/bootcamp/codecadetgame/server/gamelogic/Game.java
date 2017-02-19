package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.CollectiveChoosable;
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
    private String rollDice() {

        EventType eventType = EventType.values()[ProbManager.chooseEqual(EventType.values().length)];

        String eventToDisplay = null;

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
        return eventToDisplay;

    }

    private String collectiveChoosable() {
        CollectiveChoosable cc = CollectiveChoosable.values()
                [ProbManager.chooseEqual(CollectiveChoosable.values().length)];

        String eventToDisplay = null;

        switch (cc) {
            case QUESTIONS:
                eventToDisplay = Events.questions();
                break;
            case TIME_EVENT:


        }

        return eventToDisplay;

    }


}
