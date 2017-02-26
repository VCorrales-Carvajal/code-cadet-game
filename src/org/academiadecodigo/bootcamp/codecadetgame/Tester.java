package org.academiadecodigo.bootcamp.codecadetgame;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by codecadet on 2/19/17.
 */
public class Tester {

    public static void main(String[] args) {

        Tester tester = new Tester();

//        tester.probabilityTester();

        // Testing rendering of player positions

        //tester.renderPlayersPositionsTester();

//        tester.renderPlayersPositionsTester();


        // Testing if iteration of map works
//        tester.mapIterationTester();

        // Testing if shuffling indexes works
        // tester.shuffleIndicesTester();

//        String s = EventType.QUESTION.toString();
//        System.out.println(s);

//        System.out.println(ServerHelper.insertNumberOfSteps());

//        System.out.println(GameHelper.cowWisdomQuote());
        System.out.println(GameHelper.welcome());
    }

    private void shuffleIndicesTester() {
        int[] indices;
        for (int i = 1; i < 11; i++) {
            indices = GameHelper.shuffleIndexArray(i);

            String s = "";
            for (int j : indices) {
                System.out.print(j + " ");
                s = s + " " + j;
            }
            System.out.println();
        }
    }


    private void renderPlayersPositionsTester() {
        int[] playersPositions = {4, 2, 8};
        String[] usernames = {"vero", "micael", "to"};
        System.out.println(GameHelper.renderPlayersPosition(playersPositions, usernames,10));
    }

    private void mapIterationTester() {
        String[] array = new String[3];
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Set<Integer> keys = map.keySet();
        int i = 0;
        for (Integer k : keys) {
            array[i] = map.get(k);
            i++;
        }

        for (String s : array) {
            System.out.println("Position in map: " + s);
        }
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
