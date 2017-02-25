package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.*;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game implements Runnable {

    private Server server;
    private Event[] events = new Event[EventType.values().length];
    private int currentPlayerCounter = 0;


    public Game(Server server) {
        this.server = server;

        events[EventType.QUESTION.ordinal()] = new Question(server);
        events[EventType.TIME_EVENT.ordinal()] = new TimeEvent(server);
        events[EventType.LIFE_DECISION.ordinal()] = new LifeDecision(server);
        events[EventType.PERSONAL_EVENT.ordinal()] = new PersonalEvent(server);
        events[EventType.COLLECTIVE_EVENT.ordinal()] = new CollectiveEvent(server);
    }

    public void start() {

        server.sendMsgToAll(GameHelper.gettingOutOfAC());
        events[EventType.QUESTION.ordinal()].process("All");
        turnCycle();

    }


    private void turnCycle() {

        String currentPlayerUsername;

        while (noOneFinished()) {

            currentPlayerUsername = server.getPlayerDispatcherList().get(currentPlayerCounter).getPlayer().getUsername();

            // Send message to all players informing the player in the current turn
            server.sendMsgToAll(GameHelper.informCurrentPlayer(currentPlayerUsername));

            // Select an Event randomly
            EventType eventType = EventType.choose();
            Event event = events[eventType.ordinal()];

            // The selected event sends a statement and processes consequences accordingly
            if (eventType.isCollective()) {
                event.process("All");
            } else {
                event.process(currentPlayerUsername);
            }

            String lifeAreaPositionString = "Dear " + currentPlayerUsername + ", your points in ";
            for (int i = 0; i < LifeArea.values().length; i++) {
                lifeAreaPositionString = lifeAreaPositionString + LifeArea.values()[i] + "are : " + server.getPlayerDispatcherTable().get(currentPlayerUsername).getPlayer().getLifeAreasPosition()[i] + "\n";
            }
            server.sendMsgToAll(lifeAreaPositionString);
            GameHelper.renderPlayersPosition(GameHelper.getPlayersPositions(server));

            if (Math.random() < Constants.PROB_COW_WISDOM_QUOTE) {
                GameHelper.displayCowWisdomQuote();
            }

            updatePlayerCounter(server.getPlayerDispatcherList().size());
        }
    }

    private boolean noOneFinished() {

        for (int i = 0; i < GameHelper.getPlayersPositions(server).length - 1; i++) {
            if (GameHelper.getPlayersPositions(server)[i] == server.getStepsToFinish()) {

                return true;
            }
        }

        return false;
    }

    private void updatePlayerCounter(int length) {

        currentPlayerCounter++;
        if (currentPlayerCounter == length) {
            currentPlayerCounter = 0;
        }

    }

    public Event[] getEvents() {
        return events;
    }

    @Override
    public void run() {

        start();
    }

    @Override
    public String toString() {
        return "Game";
    }
}
