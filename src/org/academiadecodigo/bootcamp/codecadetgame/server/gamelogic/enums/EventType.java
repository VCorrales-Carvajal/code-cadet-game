package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums;

/**
 * Created by codecadet on 2/18/17.
 */
public enum EventType {
    COLLECTIVE_CHOOSABLE(0.25, true),
    COLLECTIVE_NON_CHOOSABLE(0.5, false),
    PERSONAL_CHOOSABLE(0.75, true),
    PERSONAL_NON_CHOOSABLE(1, false);

    private double probability;
    private boolean choosable;

    EventType(double probability, boolean choosable) {
        this.probability = probability;
        this.choosable = choosable;
    }

    public double getProbability() {
        return probability;
    }

    public boolean isChoosable() {
        return choosable;
    }

    public static EventType choose() {
        double random = Math.random();
        for (int i = 0; i < EventType.values().length; i++) {
            if (random < EventType.values()[i].probability) {
                return EventType.values()[i];
            }
        }
        return PERSONAL_NON_CHOOSABLE;
    }
}
