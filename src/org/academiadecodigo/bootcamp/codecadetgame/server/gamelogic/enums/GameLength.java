package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums;

/**
 * Created by codecadet on 2/24/17.
 */
public enum GameLength {

    SHORT(10),
    MEDIUM(20),
    LONG(30);

    private int numberOfTurns;

    GameLength(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
