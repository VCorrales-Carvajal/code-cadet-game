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
public class TimeEvent implements Event  {

    private final Server server;
    private BlockingQueue<String> queue = new SynchronousQueue<>();

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
        // take from queue
    }

    @Override
    public void setAnswer(String answer) {

        synchronized (this) {
            queue.offer(answer);
        }

    }

    public String getStatement() {
        return "";
    }
}
