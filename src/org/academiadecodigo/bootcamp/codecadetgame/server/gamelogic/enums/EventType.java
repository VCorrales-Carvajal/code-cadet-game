package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums;

/**
 * Created by codecadet on 2/18/17.
 */
public enum EventType {
    COLLECTIVE_CHOOSABLE(0.25),
    COLLECTIVE_NON_CHOOSABLE(0.5),
    PERSONAL_CHOOSABLE(0.75),
    PERSONAL_NON_CHOOSABLE(1);

    private double probability;

    EventType(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public EventType choose() {
        int random = (int) Math.random();
        for (int i = 0; i < EventType.values().length; i++) {
            if (random < EventType.values()[i].probability) {
                return EventType.values()[i];
            }
        }
        return null;
    }
}
