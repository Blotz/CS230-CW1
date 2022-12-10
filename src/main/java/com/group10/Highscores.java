package com.group10;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Highscores {
    private static final String HIGHSCORES_PATH = "highscores.txt";
    private static final String HIGHSCORES_FORMAT = "\"%s\" %d %d%n";
    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    
    public static void addHighscore(String profileName, int level, int score) {
        ArrayList<String> highscores = getHighscores(level);
        if (highscores.size() < 10) {
            createHighscore(profileName, level, score);
            return;
        }
        
        for (int i = 0; i < highscores.size(); i++) {
            Scanner highscore = new Scanner(highscores.get(i));
            highscore.useDelimiter(" ");
            String oldProfileName = highscore.next();  // Throw away the name
            int highscoreScore = highscore.nextInt();
            if (score > highscoreScore) {
                replaceHighscore(
                  profileName,
                  level,
                  score,
                  String.format(HIGHSCORES_FORMAT, oldProfileName, level, highscoreScore)
                );
                break;
            }
        }
    }
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
            if (highscore.equals(oldHighscore)) {
                fileContents += String.format(HIGHSCORES_FORMAT, profileName, level, score);
            } else {
                fileContents += String.format(highscore);
            }
        }
        in.close();
        
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
    
    public static ArrayList<String> getHighscores(int targetLevel) {
        InputStream file = Highscores.class.getResourceAsStream(HIGHSCORES_PATH);
        if (file == null) {
            throw new RuntimeException(FILE_NOT_FOUND);
        }
        
        ArrayList<String> highscores = new ArrayList<String>();
        Scanner in = new Scanner(file);
        
        while (in.hasNext()) {
            String line = in.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(" ");
            String profileName = lineScanner.next();
            int level = lineScanner.nextInt();
            int score = lineScanner.nextInt();
            if (level == targetLevel) {
                highscores.add(String.format("%s %d", profileName, score));
            }
        }
        return highscores;
    }
    
    
}
