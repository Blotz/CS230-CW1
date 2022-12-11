package com.group10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

public class Menu {
    @FXML
    private TextField playerName;

    private static final int MAX_COLS_OF_LEVEL_SELECT = 3;
    
    public static void mainMenu() {
        Scene scene = Main.getScene("GUI/mainMenu.fxml");
        Parent root = scene.getRoot();
        
        if (!Profile.getLevel().equals("")) {
            System.out.println("Level: " + Profile.getLevel());
            Button loadLevelButton = (Button) root.lookup("#levelLoadButton");
    
            // Make visible
            loadLevelButton.setDisable(false);
        }
        
        Main.changeScene(scene);
    }

    public static void profileMenu() {
        Main.changeScene(Main.getScene("GUI/profileMenu.fxml"));
    }

    @FXML
    public void setPlayerName(ActionEvent event) {
        System.out.println("Player Name: " + playerName.getText());
        Profile.loadProfile(playerName.getText());
        System.out.println("Profile loaded");
        System.out.println(Profile.getMaxLevel());
        
        mainMenu();
    }

    @FXML void loadLevel(ActionEvent event) {
        System.out.println("Loading level: " + Profile.getLevel());
        String[] levelString = Profile.getLevel().split("\n", 2);
        System.out.println(Arrays.toString(levelString));
        int levelNumber = Integer.parseInt(levelString[0]);
        String levelData = levelString[1];
        Level level = new Level(levelData, levelNumber);
        Game.setLevel(level);
        Game.start();
    }
    @FXML
    public void levelSelect(ActionEvent event) {
        System.out.println("Level Select");
        Scene scene = Main.getScene("GUI/levelSelect.fxml");
        Parent root = scene.getRoot();
        // Find the grid
        GridPane grid = (GridPane) root.lookup("#grid");
        
        // Add the levels
        // Read levels from level folder using resources
        File folder = new File(Menu.class.getResource("level").getFile());
        File[] files = folder.listFiles();
        Arrays.sort(files);
        
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            System.out.println(filename);
            // Create a button which points to that level
            // Add the button to the grid
            int levelNum = Integer.parseInt(filename.replaceAll("[\\D]", ""));
            if (Profile.getMaxLevel() >= levelNum) {
                VBox vbox = new VBox();
                Button button = new Button(filename.replace(".txt", ""));
                button.setOnAction(e -> {
                    String levelPath = "level/" + filename;
                    loadLevel(levelPath, levelNum);
                });
                vbox.getChildren().add(button);
                vbox.setAlignment(Pos.CENTER);
                grid.add(vbox, i % MAX_COLS_OF_LEVEL_SELECT, i / MAX_COLS_OF_LEVEL_SELECT);
            }
        }
        Main.changeScene(scene);
    }
    @FXML
    public void fullscreen(KeyEvent event) {
        Main.toggleFullScreen(event.getCode());
        event.consume();
    }
    private static void loadLevel(String name, int levelNum) {
        System.out.println("Loading level: " + name);
        // Load the level
        String levelData;
        try {
            levelData = Level.loadLevel(name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Level level = new Level(levelData, levelNum);
        
        // Load the game
         Game.setLevel(level);
         Game.start();
    }
    
    @FXML
    public void highScores() {
        System.out.println("High Scores");

        Scene scene = Main.getScene("GUI/highscoreSelect.fxml");
        Parent root = scene.getRoot();

        // Find the grid
        GridPane grid = (GridPane) root.lookup("#grid");

        // Add the levels
        // Read levels from level folder using resources
        File folder = new File(Menu.class.getResource("level").getFile());
        File[] files = folder.listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            // Create a button which points to that level
            // Add the button to the grid
            int levelNum = Integer.parseInt(filename.replaceAll("[\\D]", ""));
            if (Profile.getMaxLevel() >= levelNum) {
                VBox vbox = new VBox();
                Button button = new Button(filename.replace(".txt", ""));
                button.setOnAction(e -> {
                    loadHighscore(levelNum);
                });
                vbox.getChildren().add(button);
                vbox.setAlignment(Pos.CENTER);
                grid.add(vbox, i % MAX_COLS_OF_LEVEL_SELECT, i / MAX_COLS_OF_LEVEL_SELECT);
            }
        }
        Main.changeScene(scene);
    }

    private static void loadHighscore(int levelNum) {
        System.out.println("Loading scoreboard for level: " + levelNum);

        Scene scene = Main.getScene("GUI/highScore.fxml");
        Parent root = scene.getRoot();
        // Find the grid
        GridPane grid = (GridPane) root.lookup(".grid");
        
        ArrayList<String> highscores = Highscores.getHighscores(levelNum);
        String[] playerData = new String[highscores.size()];

        // Converts arraylist into an array of strings
        for (int i = 0; i < highscores.size(); i++) {
            playerData[i] = highscores.get(i);
        }

        Arrays.sort(playerData, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // Extracts the 2 scores
                int score1 = Integer.parseInt(s1.split(" ")[1]);
                int score2 = Integer.parseInt(s2.split(" ")[1]);

                // Compares scores and returns result
                return Integer.compare(score2, score1);
            }
        });

        ArrayList<String> sortedScores = new ArrayList<>();

        // Converts sorted array of string into an arraylist again
        for (String s : playerData) {
            sortedScores.add(s);
        }
    
        // populates the highscore screen with players in order
        for (int i = 0; i < 10; i++) {
            
            if (playerData[i] == null) {
                break;
            }
            String[] player = playerData[i].split("\"");
            String name = player[1];
            String score = player[2];
    
            VBox vboxl = new VBox();
            VBox vboxr = new VBox();
            Text nameText = new Text(name);
            Text scoreText = new Text(score);
            
            vboxl.getChildren().add(nameText);
            vboxr.getChildren().add(scoreText);
            
            vboxl.setAlignment(Pos.CENTER);
            vboxr.setAlignment(Pos.CENTER);
            
            grid.add(vboxl, 0, i+1);
            grid.add(vboxr, 1, i+1);
        }
        Main.changeScene(scene);
    }
    
    @FXML
    public void mainMenu(ActionEvent event) {
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));
    }


    
    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
