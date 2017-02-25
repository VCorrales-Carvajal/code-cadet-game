package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.Game;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

/**
 * Created by codecadet on 2/22/17.
 */
public class Question implements ChoosableEvent {

    public static final int LENGTH_QUESTIONS = 5;
    private final Server server;
    String[] questions;
    String[] correctAnswer;
    int[] steps;

    private int[] shuffledIndexes;
    private int counterIndex = 0;


    public Question(Server server) {
        this.server = server;
        init();
    }


    //TODO MICAEL: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
    @Override
    public void process(String username) {

        if (!username.equals("All")) {
            System.out.println("QuestionEvent not processing accordingly");
            return;
        }

        // Display selected statement
        //TODO: Include the first question and narrative about leaving the AC
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = GameHelper.JAVAQuestion() + questions[index];
        server.sendMsgToAll(MsgFormatter.gameMsg(eventToDisplay));

        // Listen to the answer of all players
        for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
            pd.setActive(true);
            pd.setCurrentEvent(this);
        }

        // Process answer
        processAnswer(index);

        // Increase counter
        counterIndex++;
        if (counterIndex == LENGTH_QUESTIONS) {
            counterIndex = 0;
        }

    }

    private void processAnswer(int index) {

        //TODO Micael:
        // check if there's a winner and update consequence String.
        //If there's a winner call informLifeAreaAffected, else send msg to all saying no one answered correctly
        //When player chooses an option that is not available, assume it's wrong

        String winner = null; // winner username

        // Update winner's position
        GameHelper.updateOnePlayerPosition(steps[index], winner, server, LifeArea.CAREER);

        // Send message to all showing what happened
//        server.sendMsgToAll(getConsequenceString(index, winner));
        server.sendMsgToAll(GameHelper.informLifeAreaAffected(winner, steps[index], LifeArea.CAREER, eventType));

    }


    private void init() {

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_QUESTIONS);

        questions = new String[LENGTH_QUESTIONS];
        correctAnswer = new String[LENGTH_QUESTIONS];
        steps = new int[LENGTH_QUESTIONS];

        questions[0] = "What is the difference between a class with only abstract methods and an interface?\n" +
                "\t1. An interface can have multiple inheritance, while an abstract class cannot\n" +
                "\t2. Methods have to be overridden\n" +
                "\t3. An abstract class can have properties and an interface cannot\n" +
                "\t4. There is no difference\n";
        correctAnswer[0] = "1";
        steps[0] = 1;

        questions[1] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2. Bytecode\n" +
                "\t3. Morse code\n" +
                "\t4. Usain Bolt\n";
        correctAnswer[1] = "2";
        steps[1] = 1;


    }

    public String getStatement() {
        return "";
    }

    @Override
    public void chooseAnswer(String answer) {

    }

    public String getConsequenceString(int index, String winner) {

        int step = steps[index];
        if (winner.equals("")){
            return "No one got it right! No one moves forward this turn!";
        }

        String stepString = (step != 0) ? " steps" : " step";
        return winner + ": you moved forward in your career! Advance " + step + stepString + "!";

    }
}
