package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.PlayerDispatcher;
import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventStringType;
import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.EventType;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Rolls dice and controls turns
 */

public class Game {

    private Server server;

    public Game(Server server) {

        this.server = server;
    }

    public void start() {

    }

    //TODO Micael&Vero in class Question: When player chooses an option that is not available, assume it's wrong
    private void turnCycle() {

    }

    private int[] getPlayersPositions() {

        int[] playersPositions = new int[server.getPlayerDispatcherList().size()];

        for (int i = 0; i < server.getPlayerDispatcherList().size(); i++) {

            playersPositions[i] = server.getPlayerDispatcherList().get(i).getPlayer().getPosition();

        }

        return playersPositions;

    }

    private void affectAllPlayers(int change) {
        for (PlayerDispatcher pd : server.getPlayerDispatcherList()) {
            pd.getPlayer().setPosition(pd.getPlayer().getPosition() + change);
        }
    }

    //TODO Micael: create affectOnePlayer

}
