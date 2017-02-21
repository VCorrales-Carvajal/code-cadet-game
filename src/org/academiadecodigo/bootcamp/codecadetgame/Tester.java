package org.academiadecodigo.bootcamp.codecadetgame;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.MsgHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;

/**
 * Created by codecadet on 2/19/17.
 */
public class Tester {

    public static void main(String[] args) {

        Tester tester = new Tester();
        tester.probabilityTester();

        int[] playersPositions = {4,2,8};

        MsgHelper msgHelper = new MsgHelper();
        System.out.println(msgHelper.displayPlayersPosition(playersPositions));

    }

    private void probabilityTester() {

        int et1 = 0;
        int et2 = 0;
        int et3 = 0;
        int et4 = 0;

        for (int i = 0; i < 100; i++) {

            EventType eventType = EventType.choose();
            if (eventType == EventType.COLLECTIVE_CHOOSABLE) {
                et1++;
            } else if(eventType == EventType.COLLECTIVE_NON_CHOOSABLE) {
                et2++;
            } else if(eventType == EventType.PERSONAL_CHOOSABLE) {
                et3++;
            } else if(eventType == EventType.PERSONAL_NON_CHOOSABLE) {
                et4++;
            }
            System.out.println(eventType);
            System.out.println(et1 + " " + et2 + " " + et3 + " " + et4);


        }

        System.out.println(et1 + " " + et2 + " " + et3 + " " + et4);

    }
}
