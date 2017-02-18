package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

/**
 * Created by codecadet on 2/18/17.
 */
public class Factory {

    public static Player createPlayer(String username) {
        return new Player(username);
    }
}
