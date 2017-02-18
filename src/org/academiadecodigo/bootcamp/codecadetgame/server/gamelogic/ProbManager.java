package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 * Sets the probabilities in the game
 */

public class ProbManager {

    public static int chooseEqual(int length) {
        return (int) (Math.random()*(length));
    }
}
