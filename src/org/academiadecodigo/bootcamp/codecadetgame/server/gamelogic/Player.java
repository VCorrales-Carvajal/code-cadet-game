package org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic;

import org.academiadecodigo.bootcamp.codecadetgame.server.gamelogic.enums.LifeAreas;

/**
 * Created by ToAlmeida, joaobonifacio, MicaelCruz and VCorrales-Carvajal on 2/18/17.
 */
public class Player {

    private String username;
    private int globalPosition;
    private int[] lifeAreasPosition;

    public Player(String username) {
        this.username = username;

        lifeAreasPosition = new int[LifeAreas.values().length];

    }

    public String getUsername() {
        return username;
    }

    public void setGlobalPosition(int globalPosition) {
        this.globalPosition = globalPosition;
    }

    public int getGlobalPosition() {
        return globalPosition;
    }

    public int[] getLifeAreasPosition() {
        return lifeAreasPosition;
    }

    public void setLifeAreasPosition(int lifeAreasPosition, LifeAreas lifeArea) {

        this.lifeAreasPosition[lifeArea.ordinal()] = lifeAreasPosition;
    }
}
