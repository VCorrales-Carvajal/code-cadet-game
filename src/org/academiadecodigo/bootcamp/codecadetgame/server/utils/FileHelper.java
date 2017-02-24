package org.academiadecodigo.bootcamp.codecadetgame.server.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by codecadet on 2/24/17.
 */
public class FileHelper {

    public static String readFile(String file) {

        String result = "";

        try {

            System.out.println("loading from " + file);

            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);

            String line = "";
            while ((line = bReader.readLine()) != null) {
                result += line + "\n";
            }

            bReader.close();

        } catch (IOException ex) {

            System.out.println("loading error: " + ex.getMessage());

        } finally {
            return result;
        }
    }
}
