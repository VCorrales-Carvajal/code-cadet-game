package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.*;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

import java.util.Set;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game {

    private Server server;
    private Question question;
    private TimeEvent timeEvent;
    private LifeDecision lifeDecision;
    private CollectiveEvent collectiveEvent;
    private PersonalEvent personalEvent;


    public Game(Server server) {

        this.server = server;
        question = new Question(server);
        timeEvent = new TimeEvent(server);
        lifeDecision = new LifeDecision(server);
        collectiveEvent = new CollectiveEvent(server);
        personalEvent = new PersonalEvent(server);

    }


    public void start() {

        server.sendMsgToAll(GameHelper.gettingOutOfAC());
        question.process();
        turnCycle();

    }


    private void turnCycle() {

        while (noOneFinished()) {

            EventType eventType = EventType.choose();
            Event event = getEvent(eventType);
            event.process();

            GameHelper.renderPlayersPosition(getPlayersPositions());

            if (Math.random() < Constants.PROB_COW_WISDOM_QUOTE) {
                GameHelper.displayCowWisdomQuote();
            }
        }
    }


    private Event getEvent(EventType eventType) {
        Event event = null;

        switch (eventType){
            case QUESTION:
                event = question;
                break;
            case TIME_EVENT:
                event = timeEvent;
                break;
            case LIFE_DECISION:
                event = lifeDecision;
                break;
            case COLLECTIVE_EVENT:
                event = collectiveEvent;
                break;
            case PERSONAL_EVENT:
                event = personalEvent;
                break;
        }
        return event;
    }


    private int[] getPlayersPositions() {

        int[] playersPositions = new int[server.getPlayerDispatcherTable().size()];
        Set<String> usernames = server.getPlayerDispatcherTable().keySet();

        int i = 0;
        for (String username : usernames) {
            playersPositions[i] = server.getPlayerDispatcherTable().get(username).getPlayer().getPosition();
            i++;
        }

        return playersPositions;
    }


    private void updatePlayersPositions(int change) {

        Set<String> usernames = server.getPlayerDispatcherTable().keySet();
        for (String username : usernames) {
            PlayerDispatcher pd = server.getPlayerDispatcherTable().get(username);
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }

    }

    private void updateOnPlayerPosition(int change, String username) {
        int prevPos = server.getPlayerDispatcherTable().get(username).getPlayer().getPosition();
        //Set new position
        server.getPlayerDispatcherTable().get(username).getPlayer().setPosition(prevPos + change);
    }


    private boolean noOneFinished() {
        //TODO Bonifacio: compare each player's positions to server.stepsToFinish
        throw new UnsupportedOperationException();
    }

}
