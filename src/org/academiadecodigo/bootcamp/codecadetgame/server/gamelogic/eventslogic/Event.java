package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;

/**
 * Created by codecadet on 2/22/17.
 */
public interface Event {

    void process(String username);

    void setAnswer(String answer);
}
