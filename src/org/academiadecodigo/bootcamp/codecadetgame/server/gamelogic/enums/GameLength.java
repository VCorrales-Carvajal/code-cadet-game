package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums;

/**
 * Created by codecadet on 2/24/17.
 */
public enum GameLength {

    SHORT(10),
    MEDIUM(15),
    LONG(30);

    private int numberOfSteps;

    GameLength(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }
}
