package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

/**
 * Created by codecadet on 2/24/17.
 */
public interface ChoosableEvent extends Event{

    void chooseAnswer(String answer, String username);
}
