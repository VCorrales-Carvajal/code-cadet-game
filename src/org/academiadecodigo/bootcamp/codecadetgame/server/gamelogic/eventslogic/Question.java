package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

import java.util.Scanner;

/**
 * Created by codecadet on 2/22/17.
 */
public class Question implements Event  {

//    private final Server server;
    private final int numberOfQuestions = 5;
    String[] questions = new String[numberOfQuestions];
    String[] correctAnswer = new String[numberOfQuestions];
    int[] numberOfSteps = new int[numberOfQuestions];

    public Question() {
        init();
    }

//    public Question(Server server) {
//        this.server = server;
//    }



    //TODO MICAEL: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
    @Override
    public void process() {


        Scanner scanner = new Scanner(System.in);

        System.out.println(questions[0]);

        System.out.print("Your answer: ");
        String optionChosen = scanner.nextLine();

        if (optionChosen.equals(correctAnswer[0])){
            System.out.println("Your answer is correct! you advance these steps: " + numberOfSteps[0]);
        } else {
            System.out.println("You loose!");
        }


    }


    //TODO: When player chooses an option that is not available, assume it's wrong
    //TODO: Include the first question and narrative about leaving the AC



    private void init(){


        questions[0] = GameHelper.gettingOutOfAC() + "\n" +
                "What is the difference between a class with only abstract methods and an interface?\n" +
                "\t1. An interface can have multiple inheritance, while an abstract class cannot\n" +
                "\t2. Methods have to be overridden\n" +
                "\t3. An abstract class can have properties and an interface cannot\n" +
                "\t4. There is no difference\n";
        correctAnswer[0] = "1";
        numberOfSteps[0] = 1;

        questions[1] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2. Bytecode\n" +
                "\t3. Morse code\n" +
                "\t4. Usain Bolt\n";
        correctAnswer[1] = "2";
        numberOfSteps[1] = 1;


    }

}
