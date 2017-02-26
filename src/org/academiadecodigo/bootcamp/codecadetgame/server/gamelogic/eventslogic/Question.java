package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeArea;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.MsgFormatter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by codecadet on 2/22/17.
 */
public class Question implements ChoosableEvent {

    private final EventType eventType = EventType.QUESTION;
    public static final int LENGTH_QUESTIONS = 11;
    private final Server server;
    String[] questions;
    String[] correctAnswer;
    int[] steps;
    private BlockingQueue<String[]> queue;

    private int[] shuffledIndexes;
    private int counterIndex = 0;

    public Question(Server server) {
        this.server = server;
        init();
    }


    @Override
    public void process(String username) {

        if (!username.equals(GameHelper.COLLECTIVE_USERNAME)) {
            System.out.println("QuestionEvent not processing accordingly");
            return;
        }

        // Display selected statement
        int index = shuffledIndexes[counterIndex];
        String eventToDisplay = GameHelper.displayEventType(username, eventType) + questions[index];
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

        String winner = null; // winner username

        synchronized (this) {
            while (queue.size() < server.getNumberOfPlayers()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    //Thread.interrupt called, no handling needed
                }
            }
        }

        while (!queue.isEmpty()) {

            String[] s = queue.poll();
            String firstAnswer = s[0];

            if (firstAnswer.equals(correctAnswer[index])) {
                winner = s[1];
                break;
            }
        }
        queue.clear();


        if (winner != null) {

            // Update winner's position
            GameHelper.updatePlayersPositions(steps[index], winner, server, LifeArea.CAREER);
            // Send message to all showing what happened
            server.sendMsgToAll(GameHelper.informWinner(winner));
            server.sendMsgToAll(GameHelper.informLifeAreaAffected(winner, steps[index], LifeArea.CAREER, eventType));

        } else {

            server.sendMsgToAll(GameHelper.invalidAnswer());

        }

    }


    private void init() {

        queue = new ArrayBlockingQueue<String[]>(server.getNumberOfPlayers());

        shuffledIndexes = GameHelper.shuffleIndexArray(LENGTH_QUESTIONS);

        questions = new String[LENGTH_QUESTIONS];
        correctAnswer = new String[LENGTH_QUESTIONS];
        steps = new int[LENGTH_QUESTIONS];

        questions[0] = "What is the difference between a class with only abstract methods and an interface?\n" +
                "\t1. An interface can have multiple inheritance, while an abstract class cannot\n" +
                "\t2. Methods have to be overridden\n" +
                "\t3. There is no difference\n";
        correctAnswer[0] = "1";
        steps[0] = 2;

        questions[1] = "What runs on the JVM?\n" +
                "\t1. Machine code\n" +
                "\t2. Bytecode\n" +
                "\t3. Morse code\n" +
                "\t4. Usain Bolt\n";
        correctAnswer[1] = "2";
        steps[1] = 1;

        questions[2] = "Which of these tools is used to compile java code?\n" +
                "\t1. jar\n" +
                "\t2. javac\n" +
                "\t3. javadoc\n" +
                "\t4. java\n";
        correctAnswer[2] = "2";
        steps[2] = 1;

        questions[3] = "Which of these tools is used to execute java code?\n" +
                "\t1. rmic\n" +
                "\t2. javac\n" +
                "\t3. javadoc\n" +
                "\t4. java\n";
        correctAnswer[3] = "4";
        steps[3] = 1;

        questions[4] = "Jar stands for _____________.\n" +
                "\t1. Java Archive Runner\n" +
                "\t2. Java Archive\n" +
                "\t3. Java Application Runner\n" +
                "\t4. none of these\n";
        correctAnswer[4] = "2";
        steps[4] = 2;

        questions[5] = "Which of the following is not a primitive data type?\n" +
                "\t1. int\n" +
                "\t2. short\n" +
                "\t3. byte\n" +
                "\t4. enum\n";
        correctAnswer[5] = "4";
        steps[5] = 1;

        questions[6] = "The range of a Byte Data Type is ____________.\n" +
                "\t1. -128 to 256\n" +
                "\t2. -127 to 128\n" +
                "\t3. -128 to 255\n" +
                "\t4. -128 to 127\n";
        correctAnswer[6] = "4";
        steps[6] = 2;

        questions[7] = "Java was designed by ______________.\n" +
                "\t1. Microsoft\n" +
                "\t2. Mozilla Corporation\n" +
                "\t3. Sun Microsystems\n" +
                "\t4. Amazon Inc.\n";
        correctAnswer[7] = "3";
        steps[7] = 1;

        questions[8] = "What kind of language java is?\n" +
                "\t1. Event Driven\n" +
                "\t2. Procedural\n" +
                "\t3. Object Oriented\n" +
                "\t4. None of these\n";
        correctAnswer[8] = "3";
        steps[8] = 1;

        questions[9] = "Java promises the following - 'Write Once, Run Anywhere'.\n" +
                "\t1. True\n" +
                "\t2. False\n";
        correctAnswer[9] = "1";
        steps[9] = 1;

        questions[10] = "JVM stands for __________________.\n" +
                "\t1. Java Virtual Machine\n" +
                "\t2. Java Virtual Machanism\n" +
                "\t3. Java Virtual Management\n" +
                "\t4. Java Virtual Memory\n";
        correctAnswer[10] = "1";
        steps[10] = 1;


    }


    @Override
    public void chooseAnswer(String answer, String username) {

        synchronized (queue) {
            String[] answerAndUsername = {answer, username};
            queue.offer(answerAndUsername);
        }

        synchronized (this) {
            if (queue.size() == server.getNumberOfPlayers()){
                notifyAll();
            }
        }

    }

}
