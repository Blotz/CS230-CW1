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
import java.util.ArrayList;

public class Menu {
    private static ProfileHandler profileHandler;
    private static Profile userProfile;
    @FXML
    private TextField playerName;

    private static final int MAX_COLS_OF_LEVEL_SELECT = 3;
    private static final int MAX_COLS_OF_PROFILE_SELECT = 5;
    
    public static void mainMenu() {
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));
    }

    public static void profileMenu() {
        try {
            profileHandler = new ProfileHandler();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Main.changeScene(Main.getScene("GUI/profileMenu.fxml"));
    }

    @FXML
    public void createProfileMenu(ActionEvent event) {
        System.out.println("Create Profile");
        Scene scene = Main.getScene("GUI/createProfileMenu.fxml");
        Parent root = scene.getRoot();
        GridPane grid = (GridPane) root.lookup("#grid");
        Main.changeScene(scene);
    }

    @FXML
    public void createProfile(ActionEvent event) {
        profileHandler.createProfile(playerName.getText());
        ArrayList<Profile> profiles = profileHandler.selectProfiles();
        for (Profile p : profiles) {
            if (p.getPlayerName().equals(playerName.getText())) {
                userProfile = p;
            }
        }
        mainMenu();
    }

    @FXML
    public void selectProfile(ActionEvent event) {
        System.out.println("Select Profile");
        Scene scene = Main.getScene("GUI/selectProfile.fxml");
        Parent root = scene.getRoot();
        // Find the grid
        GridPane grid = (GridPane) root.lookup("#grid");
        Main.changeScene(scene);

        ArrayList<Profile> profiles = profileHandler.selectProfiles();
        for (int i = 0; i <profiles.size(); i++) {
            Button button = new Button(profiles.get(i).getPlayerName());
            int finalI = i;
            button.setOnAction(e -> {
                userProfile = profiles.get(finalI);
                mainMenu();
            });
            grid.add(button, i % MAX_COLS_OF_PROFILE_SELECT , i / MAX_COLS_OF_PROFILE_SELECT);
        }
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
        
        
        System.err.println("Warning: levels are loaded out of order");
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            System.out.println(filename);
            // Create a button which points to that level
            // Add the button to the grid
            int levelNum = Integer.parseInt(filename.replaceAll("[\\D]", ""));
            if (userProfile.getMaxLevel() >= levelNum) {
                Button button = new Button(filename);
                button.setOnAction(e -> {
                    String levelPath = "level/" + filename;
                    loadLevel(levelPath);
                });
                grid.add(button, i % MAX_COLS_OF_LEVEL_SELECT, i / MAX_COLS_OF_LEVEL_SELECT);
            }
        }
        Main.changeScene(scene);
    }
    
    private static void loadLevel(String name) {
        System.out.println("Loading level: " + name);
        // Load the level
        Level level;
        try {
            level = new Level(name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.err.println("Warning: level select isnt fully implemented. will only load level1");
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
