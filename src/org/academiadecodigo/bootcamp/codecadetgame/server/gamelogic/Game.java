package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.*;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

import java.util.Set;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game implements Runnable {

    private Server server;
    private Event[] events = new Event[EventType.values().length];
    private String[] usernames;

    private int currentPlayerCounter = 0;
    private int turnCounter = 0;
    private String currentAnswer;


    public Game(Server server) {
        this.server = server;
        init();
    }

    public void start() {
        server.sendMsgToAll((GameHelper.startGame()));
        server.sendMsgToAll(GameHelper.gettingOutOfAC());
        events[EventType.QUESTION.ordinal()].process(GameHelper.COLLECTIVE_USERNAME);
        turnCycle();
    }


    private void turnCycle() {

        String currentPlayerUsername;

        while (turnCounter <= server.getTurnsToFinish()) {

            currentPlayerUsername = usernames[currentPlayerCounter];

            // Send message to all players informing the player in the current turn
            server.sendMsgToAll(GameHelper.informCurrentPlayer(currentPlayerUsername));

            // Activate player's thread and listen for keypress to roll the dice
            awaitPlayerAnswer(currentPlayerUsername);

            // Select an Event randomly
            EventType eventType = EventType.choose();
            Event event = events[eventType.ordinal()];

            // The selected event sends a statement and processes consequences accordingly
            if (eventType.isCollective()) {
                event.process(GameHelper.COLLECTIVE_USERNAME);
            } else {
                event.process(currentPlayerUsername);
            }

            threadSleep();

            // Inform players of their state in life
            server.sendMsgToAll(GameHelper.renderPlayersPosition(server, GameHelper.getPlayerPositions(server), usernames, server.getTurnsToFinish()));

            threadSleep();

            // The cow appears with a JAVA wisdom quote
            if (Math.random() < GameHelper.PROB_COW_WISDOM_QUOTE) {
                server.sendMsgToAll(GameHelper.cowWisdomQuote());
            }

            // Update player counter
            updatePlayerCounter(server.getPlayerDispatcherList().size());

            // Update turn counter
            turnCounter++;
        }

        endGame();
    }

    private void init() {

        events[EventType.QUESTION.ordinal()] = new Question(server);
        events[EventType.TIME_EVENT.ordinal()] = new TimeEvent(server);
        events[EventType.LIFE_DECISION.ordinal()] = new LifeDecision(server);
        events[EventType.PERSONAL_EVENT.ordinal()] = new PersonalEvent(server);
        events[EventType.COLLECTIVE_EVENT.ordinal()] = new CollectiveEvent(server);

        Set<String> s = server.getPlayerDispatcherMap().keySet();
        usernames = s.toArray(new String[s.size()]);
    }

    private void awaitPlayerAnswer(String currentPlayerUsername) {
        server.getPlayerDispatcherMap().get(currentPlayerUsername).setActive(true);

        synchronized (this) {

            while (currentAnswer == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //Thread.interrupt called, no handling needed
                }
            }
        }

        currentAnswer = null;
    }

    public void sendInputToGame(String playerInput) {
        synchronized (this) {
            currentAnswer = playerInput;
            notifyAll();
        }
    }

    private void endGame() {

        String absoluteWinner = null;
        int maxPosition = 0;
        for (int i = 0; i < usernames.length; i++) {
            int playerPos = server.getPlayerDispatcherMap().get(usernames[i]).getPlayer().getGlobalPosition();
            if (playerPos > maxPosition) {
                absoluteWinner = server.getPlayerDispatcherList().get(i).getPlayer().getUsername();
                maxPosition = playerPos;
            }
        }

        server.sendMsgToAll(GameHelper.endGame(absoluteWinner));
    }

    private void threadSleep() {
        try {
            Thread.sleep(GameHelper.GAME_THREAD_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
