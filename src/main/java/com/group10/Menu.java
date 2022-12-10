package com.group10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

public class Menu {
    @FXML
    private TextField playerName;

    private static final int MAX_COLS_OF_LEVEL_SELECT = 3;
    
    public static void mainMenu() {
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));
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
        System.out.println("Current level saved");
        System.out.println(Profile.getLevel());
        
        mainMenu();
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
                
                Button button = new Button(filename.replace(".txt", ""));
                button.setOnAction(e -> {
                    String levelPath = "level/" + filename;
                    loadLevel(levelPath, levelNum);
                });
                grid.add(button, i % MAX_COLS_OF_LEVEL_SELECT, i / MAX_COLS_OF_LEVEL_SELECT);
            }
        }
        Main.changeScene(scene);
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
        System.out.println(levelData);
        System.out.println("################################");
        System.out.println(level.saveLevel());
        
        // Load the game
        // Game.display(Main.getStage());
         Game.setLevel(level);
         Game.start();
    }
    
    @FXML
    public void highScores(ActionEvent event) {
        System.out.println("High Scores");
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
