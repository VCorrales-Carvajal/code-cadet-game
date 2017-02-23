package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.MsgHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.*;

import java.util.Set;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game {

    private Server server;

    private String eventToDisplay;
    private EventType eventType;

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


    }

    public void start() {
        server.sendMsgToAll(Events.firstGreeting());
        question.process();
        turnCycle();

    }


    //TODO: When player chooses an option that is not available, assume it's wrong
    private void turnCycle() {


        while (noOneFinished()) {

            EventType eventType = EventType.choose();
            Event event = getEvent(eventType);
            event.process();

            MsgHelper.displayPlayersPosition(getPlayersPositions());

            if (Math.random() < Constants.PROB_COW_WISDOM_QUOTE) {
                MsgHelper.displayCowWisdomQuote();
            }
        }
    }


    private boolean noOneFinished() {
        //TODO Antonio: compare each player's positions to server.stepsToFinish
        throw new UnsupportedOperationException();
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

    private int getPlayerAnswer() {
        throw new UnsupportedOperationException();
        // If personal, then await for that player's answer
        // If collective awaits for answer from all players:
        //  Depends if Question or TimeEvent
    }


    private void affectAllPlayers(int change) {

        Set<String> usernames = server.getPlayerDispatcherTable().keySet();

        for (String username : usernames) {
            PlayerDispatcher pd = server.getPlayerDispatcherTable().get(username);
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }
    }

    //TODO  : create affectOnePlayer



}
