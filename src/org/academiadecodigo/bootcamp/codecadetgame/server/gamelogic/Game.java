package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.MsgHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventStringType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game {

    private Server server;

    private String eventToDisplay;
    private EventStringType eventStringType;

    private int[] shuffledIndexesCollective;
    private int counterShuffledIndexesCollective = 0;



    public Game(Server server) {

        this.server = server;
    }

    public void start() {

        server.sendMsgToAll(Events.firstGreeting());
        shuffledIndexesCollective = shuffleIndex(Events.LENGTH_COLLECTIVE_EVENTS);
    }

    private int[] shuffleIndex(int lengthArray) {
        //returns int[] with shuffled indexes;
        throw new UnsupportedOperationException();

    }

    //TODO: When player chooses an option that is not available, assume it's wrong
    private void turnCycle() {


        EventType eventType = EventType.choose();

        int indexEventSelected;
        if (eventType.isChoosable()) {
            displayEvent(eventType);
            indexEventSelected = getPlayerAnswer();
        } else {
            indexEventSelected = displayEvent(eventType);
        }

        displayConsequence(eventStringType, indexEventSelected);


        MsgHelper.displayPlayersPosition(getPlayersPositions());

        if (Math.random() < Constants.PROB_COW_WISDOM_QUOTE) {
            MsgHelper.displayCowWisdomQuote();
        }

    }

    private int[] getPlayersPositions() {

        int[] playersPositions = new int[server.getPlayerDispatcherList().size()];

        for (int i = 0; i < server.getPlayerDispatcherList().size(); i++) {

            playersPositions[i] = server.getPlayerDispatcherList().get(i).getPlayer().getPosition();

        }

        return playersPositions;

    }

    private int getPlayerAnswer() {
        throw new UnsupportedOperationException();
        // If personal, then await for that player's answer
        // If collective awaits for answer from all players:
        //  Depends if Question or TimeEvent
    }

    private int displayEvent(EventType eventType) {

        int index;
        switch (eventType) {
            case COLLECTIVE_CHOOSABLE:
                index = collectiveChoosable();
                eventStringType = EventStringType.QUESTION;
                break;

            case COLLECTIVE_NON_CHOOSABLE:
                index = collectiveNonChoosable();
                eventStringType = EventStringType.COLLECTIVE_EVENT;
                break;

            case PERSONAL_CHOOSABLE:
                index = personalChoosable();
                eventStringType = EventStringType.LIFE_DECISION;
                break;

            case PERSONAL_NON_CHOOSABLE:
                index = personalNonChoosable();
                eventStringType = EventStringType.PERSONAL_EVENT;
                break;

            default:
                eventToDisplay = "Something is WRONG!";
                eventStringType = EventStringType.COLLECTIVE_EVENT;
                index = 0;
        }

        server.sendMsgToAll(eventToDisplay);
        return index;
    }

    private int personalChoosable() {
        throw new UnsupportedOperationException();
    }

    private int personalNonChoosable() {
        throw new UnsupportedOperationException();
    }

    private int collectiveNonChoosable() {
        int index;
        if (counterShuffledIndexesCollective == Events.LENGTH_COLLECTIVE_EVENTS) {
            counterShuffledIndexesCollective = 0;
        }
        index = shuffledIndexesCollective[counterShuffledIndexesCollective];
        eventToDisplay = Events.getCollectiveEvents()[index];
        counterShuffledIndexesCollective++;

        return index;
    }

    private void displayConsequence(EventStringType e, int indexEventSelected) {

        String consequence = null;

        switch (e){
            case QUESTION:
                break;
            case TIME_EVENT:
                break;
            case LIFE_DECISION:
                break;
            case COLLECTIVE_EVENT:
                consequence = Events.getConsequenceCollectiveEvents(indexEventSelected);
                int change = Events.getStepsChangedCollectiveEvents(indexEventSelected);
                affectAllPlayers(change);
                break;

            case PERSONAL_EVENT:
                //TODO: complete this
                break;
        }

        server.sendMsgToAll(consequence);

        // Advance or retreat players accordingly
        throw new UnsupportedOperationException();

    }

    private void affectAllPlayers(int change) {
        for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }
    }

    //TODO Micael: create affectOnePlayer

    private int collectiveChoosable() {

        EventStringType cc = EventStringType.values()
                [ProbManager.chooseEqual(EventStringType.values().length)];


        switch (cc) {
            case QUESTION:
                eventToDisplay = Events.questions();
                break;
            case TIME_EVENT:


        }

        return 0; //TODO: Improve, since this return is not used (therefore unnecessary)

    }


}
