package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;

/**
 * Created by codecadet on 2/22/17.
 */
public class Question implements Event  {

    private final Server server;

    public Question(Server server) {
        this.server = server;
    }

    @Override
    public void process() {
        //TODO MICAEL: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
    }


    //TODO: When player chooses an option that is not available, assume it's wrong
    //TODO: Include the first question and narrative about leaving the AC

    public static String questions() {

        return getQuestions()[(int) (Math.random()*(getQuestions().length))];

    }

    public static String[] getQuestions() {
        int numberOfQuestions = 2;
        String[] questions = new String[numberOfQuestions];

        questions[0] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2.Bytecode\n" +
                "\t3.Morse code\n" +
                "\t4.Usain Bolt\n";

        return questions;
    }
}
