package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.MsgHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic.*;

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


    private void affectAllPlayers(int change) {
        for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }
    }

    //TODO  : create affectOnePlayer

    private int collectiveChoosable() {

        EventType cc = EventType.values()
                [ProbManager.chooseEqual(EventType.values().length)];


        switch (cc) {
            case QUESTION:
                eventToDisplay = Events.questions();
                break;
            case TIME_EVENT:


        }

        return 0; //TODO: Improve, since this return is not used (therefore unnecessary)

    }


}
