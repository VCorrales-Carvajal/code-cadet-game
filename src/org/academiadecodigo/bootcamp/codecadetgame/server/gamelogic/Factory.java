package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;

/**
 * Created by codecadet on 2/18/17.
 */
public class Factory {

    public static Game createGame(Server server) {

        return new Game(server);

    }

    public static Player createPlayer(String username) {

        return new Player(username);
    }
}
