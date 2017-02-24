package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums;

/**
 * Created by codecadet on 2/18/17.
 */
public enum EventType {
    QUESTION(0.2, true, true),
    TIME_EVENT(0.4, true, true),
    LIFE_DECISION(0.6, true, false),
    COLLECTIVE_EVENT(0.8, false, true),
    PERSONAL_EVENT(1, false, false);

    private double probability;
    private boolean choosable;
    private boolean collective;



    EventType(double probability, boolean choosable, boolean collective) {

        this.probability = probability;
        this.choosable = choosable;
        this.collective = collective;
    }

    public double getProbability() {
        return probability;
    }

    public boolean isCollective() {
        return collective;
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
        return PERSONAL_EVENT;
    }
}
