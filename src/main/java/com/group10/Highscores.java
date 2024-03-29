package com.group10;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This handles all operations to do with highscores.
 */
public class Highscores {
    private static final String HIGHSCORES_PATH = "highscores.txt";
    private static final String HIGHSCORES_FORMAT = "\"%s\" %d %d%n";
    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    
    /**
     * Reads the highscores file and returns the highscores
     * @param profileName the name of the profile to get the highscores for
     * @param level the level to get the highscores for
     * @param score the score to add to the highscores
     */
    public static void addHighscore(String profileName, int level, int score) {
        ArrayList<String> highscores = getHighscores(level);
        if (highscores.size() < 10) {
            createHighscore(profileName, level, score);
            return;
        }
        
        for (int i = 0; i < highscores.size(); i++) {
            String highscore = highscores.get(i);
            String[] highscoreSplit = highscore.split("\"");
            String oldProfileName = highscoreSplit[1];
            int highscoreScore = Integer.parseInt(highscoreSplit[2].strip());
            if (score > highscoreScore) {
                replaceHighscore(
                  profileName,
                  level,
                  score,
                  String.format(HIGHSCORES_FORMAT, oldProfileName, level, highscoreScore).strip()
                );
                break;
            }
        }
    }
    
    /**
     * Replaces a replaces the score of a profile in the highscores file
     * @param profileName the name of the profile to replace the score for
     * @param level the level to replace the score for
     * @param score the score to replace
     * @param oldHighscore the old highscore to replace
     *
     * @throws RuntimeException if the highscores file doesn't exist
     * @throws IOException if there is an error reading the highscores file
     * @throws IOException if there is an error writing to the highscores file
     */
    private static void replaceHighscore(String profileName, int level, int score, String oldHighscore) {
        // Read the file
        InputStream file = Highscores.class.getResourceAsStream(HIGHSCORES_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        String fileContents = "";
        
        Scanner in = new Scanner(file);
        // Loop though and find the profile account
        while (in.hasNext()) {
            String highscore = in.nextLine();
            if (!highscore.equals(oldHighscore)) {
                fileContents += String.format(highscore + "%n");
            }
        }
        in.close();
        fileContents += String.format(HIGHSCORES_FORMAT, profileName, level, score);
        String path = Highscores.class.getResource(HIGHSCORES_PATH).getPath();
        // Write the file
        try {
            OutputStream out = new FileOutputStream(path);
            out.write(fileContents.getBytes(), 0, fileContents.length());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a new highscore in the highscores file
     * @param profileName the name of the profile to create the highscore for
     * @param level the level to create the highscore for
     * @param score the score to create
     */
    private static void createHighscore(String profileName, int level, int score) {
        String path = Highscores.class.getResource(HIGHSCORES_PATH).getPath();
    
        try {
            OutputStream os = new FileOutputStream(path, true);
            String data = String.format(HIGHSCORES_FORMAT, profileName, level, score);
            os.write(data.getBytes(), 0, data.length());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns all the highscores for a level
     * @param targetLevel the level to get the highscores for
     * @return the highscores for the level
     * @throws RuntimeException if the highscores file doesn't exist
     */
    public static ArrayList<String> getHighscores(int targetLevel) {
        InputStream file = Highscores.class.getResourceAsStream(HIGHSCORES_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        
        ArrayList<String> highscores = new ArrayList<String>();
        Scanner in = new Scanner(file);
        
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineSplit = line.split("\"");
            String profileName = lineSplit[1];
            Scanner scoreNum = new Scanner(lineSplit[2].strip()).useDelimiter(" ");
            int level = scoreNum.nextInt();
            int score = scoreNum.nextInt();
            if (level == targetLevel) {
                highscores.add(String.format("\"%s\" %d", profileName, score));
            }
        }
        return highscores;
    }
    
    
    /**
     * Deletes all the highscores for a profile
     * @param profileName the name of the profile to delete the highscores for
     */
    public static void deleteHighscore(String profileName) {
        // Read the file
        InputStream file = Highscores.class.getResourceAsStream(HIGHSCORES_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        String fileContents = "";
    
        Scanner in = new Scanner(file);
        // Loop though and find the profile account
        while (in.hasNext()) {
            String highscore = in.nextLine();
            if (!highscore.startsWith("\"" + profileName + "\"")) {
                fileContents += String.format(highscore + "%n");
            }
        }
        in.close();
        // Write the file
        String path = Highscores.class.getResource(HIGHSCORES_PATH).getPath();
        try {
            OutputStream out = new FileOutputStream(path);
            out.write(fileContents.getBytes(), 0, fileContents.length());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
}
