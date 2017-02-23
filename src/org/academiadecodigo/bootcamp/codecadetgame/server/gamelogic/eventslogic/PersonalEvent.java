package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;

/**
 * Created by codecadet on 2/22/17.
 */
public class PersonalEvent implements Event  {
    private final Server server;

    public PersonalEvent(Server server) {
        this.server = server;
    }

    @Override
    public void process() {
        //TODO ANTÓNIO: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
    }
}
