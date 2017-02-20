package org.academiadecodigo.bootcamp.codecadetgame.server;

import org.academiadecodigo.bootcamp.codecadetgame.server.connection.Server;

/**
 * Created by codecadet on 2/19/17.
 */
public class Main {

    public static void main(String[] args) {

            Server server = new Server();
            server.start();

        }
}
