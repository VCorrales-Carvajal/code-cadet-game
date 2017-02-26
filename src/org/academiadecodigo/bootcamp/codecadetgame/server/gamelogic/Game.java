package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
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
    private int turnCounter = 0;


    public Game(Server server) {
        this.server = server;

        events[EventType.QUESTION.ordinal()] = new Question(server);
        events[EventType.TIME_EVENT.ordinal()] = new TimeEvent(server);
        events[EventType.LIFE_DECISION.ordinal()] = new LifeDecision(server);
        events[EventType.PERSONAL_EVENT.ordinal()] = new PersonalEvent(server);
        events[EventType.COLLECTIVE_EVENT.ordinal()] = new CollectiveEvent(server);
    }

    public void start() {

        server.sendMsgToAll((GameHelper.startGame()));
        server.sendMsgToAll(GameHelper.gettingOutOfAC());
        events[EventType.QUESTION.ordinal()].process(GameHelper.COLLECTIVE_USERNAME);
        System.out.println("Debugging: Beginning turn cycle");
        turnCycle();

    }


    private void turnCycle() {

        String[] usernames = new String[server.getPlayerDispatcherList().size()];
        for (int i = 0; i < usernames.length; i++) {
            usernames[i] = server.getPlayerDispatcherList().get(i).getPlayer().getUsername();
        }

        String currentPlayerUsername;

        while (noOneFinished() && (turnCounter <= GameHelper.MAX_TURNS)) {

            currentPlayerUsername = usernames[currentPlayerCounter];

            // Send message to all players informing the player in the current turn
//            server.sendMsgToAll(GameHelper.informCurrentPlayer(currentPlayerUsername));

            // Select an Event randomly
            EventType eventType = EventType.choose();
            Event event = events[eventType.ordinal()];

            // The selected event sends a statement and processes consequences accordingly
            if (eventType.isCollective()) {
                event.process(GameHelper.COLLECTIVE_USERNAME);
            } else {
                event.process(currentPlayerUsername);
            }

            server.sendMsgToAll(GameHelper.informLifeAreaPosition(server, currentPlayerUsername));

            server.sendMsgToAll(GameHelper.renderPlayersPosition(GameHelper.getPlayerPositions(server), usernames));

            if (Math.random() < GameHelper.PROB_COW_WISDOM_QUOTE) {
                GameHelper.displayCowWisdomQuote();
            }

            updatePlayerCounter(server.getPlayerDispatcherList().size());

            turnCounter++;
        }

        server.sendMsgToAll(GameHelper.endGame());
    }

    private boolean noOneFinished() {
        int[] playerPos = GameHelper.getPlayerPositions(server);
        for (int i = 0; i < playerPos.length; i++) {
            if (playerPos[i] == server.getStepsToFinish()) {
                return false;
            }
        }

        return true;
    }

    private void updatePlayerCounter(int length) {

        currentPlayerCounter++;
        if (currentPlayerCounter == length) {
            currentPlayerCounter = 0;
        }

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
