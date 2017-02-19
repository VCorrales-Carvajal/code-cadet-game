package org.academiadecodigo.bootcamp.codecadetgame;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;

/**
 * Created by codecadet on 2/19/17.
 */
public class Tester {

    public static void main(String[] args) {

        EventType eventType = EventType.choose();
        System.out.println(eventType);
    }
}
