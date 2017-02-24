package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.eventslogic;

import jdk.nashorn.internal.ir.IfNode;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.utils.GameHelper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by codecadet on 2/22/17.
 */
public class TimeEvent implements ChoosableEvent  {

    public static final int LENGTH_TIME_EVENTS = 5;
    private final Server server;
    private BlockingQueue<String> queue = new SynchronousQueue<>();
    String[] questions = new String[LENGTH_TIME_EVENTS];
    String[] positiveConsequence = new String[LENGTH_TIME_EVENTS];
    String[] negativeConsequence = new String[LENGTH_TIME_EVENTS];
    int[] numberOfSteps = new int[LENGTH_TIME_EVENTS];
    private int[] shuffledIndexes;

    public TimeEvent(Server server) {
        this.server = server;
    }

    //TODO MICAEL: Verifies event type and asks respective Class to resolve (send message to players, check players answers/results and update players positions))
    @Override
    public void process(String username) {

        if (!username.equals("All")){
            System.out.println("TimeEvent not processing accordingly");
            return;
        }

        String eventToDisplay = getStatement();
        server.sendMsgToAll(eventToDisplay);

        for (PlayerDispatcher pd: server.getPlayerDispatcherList()) {
            pd.setActive(true);
            pd.setCurrentEvent(this);
        }

        processAnswer();

    }

    private void processAnswer() {
        String firstAnswer = queue.poll();
    }

    private void init(){



        questions[0] = "You invest in a promising tech startup\n" +
                "\t1. Yes\n" +
                "\t2. No\n";
        positiveConsequence[0] = "The startup becomes the next instagram, you earn a lot of money";
        negativeConsequence[0] = "The startup never takes off and you lose your investment";
        numberOfSteps[0] = 1;

        questions[1] = "You go on vacations to a beautiful Caribbean island\n" +
                "\t1. Yes\n" +
                "\t2. No\n";
        positiveConsequence[1] = "You came back wonderfully tanned. Ronaldo envies you";
        negativeConsequence[1] = "You got bitten by a piranha and you lose a toe";
        numberOfSteps[1] = 1;


    }

    public String getStatement() {
        return "";
    }

    @Override
    public void chooseAnswer(String answer) {
        synchronized (this) {
            queue.offer(answer);
        }
    }
}
