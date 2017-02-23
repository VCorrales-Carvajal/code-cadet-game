package org.academiadecodigo.bootcamp.codecadetgame;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

/**
 * Created by codecadet on 2/19/17.
 */
public class Tester {

    public static void main(String[] args) {

        Tester tester = new Tester();
        tester.probabilityTester();

        int[] playersPositions = {4,2,8};

        System.out.println(GameHelper.renderPlayersPosition(playersPositions));

    }

    private void probabilityTester() {

        int[] et = new int[EventType.values().length];

        for (int i = 0; i < 100; i++) {

            EventType eventType = EventType.choose();
            switch (eventType) {
                case QUESTION:
                    ++et[eventType.QUESTION.ordinal()];
                    break;
                case TIME_EVENT:
                    ++et[eventType.TIME_EVENT.ordinal()];
                    break;
                case LIFE_DECISION:
                    ++et[eventType.LIFE_DECISION.ordinal()];
                    break;
                case COLLECTIVE_EVENT:
                    ++et[eventType.COLLECTIVE_EVENT.ordinal()];
                    break;
                case PERSONAL_EVENT:
                    ++et[eventType.PERSONAL_EVENT.ordinal()];
                    break;
            }
            System.out.println(eventType);
            System.out.println(et[0] + " " + et[1] + " " + et[2] +
                    " " + et[3] + " " + et[4]);
            System.out.println("Ordinal of Life Decision is " + eventType.LIFE_DECISION.ordinal());
        }

        System.out.println(et[0] + " " + et[1] + " " + et[2] +
                " " + et[3] + " " + et[4]);

    }
}
