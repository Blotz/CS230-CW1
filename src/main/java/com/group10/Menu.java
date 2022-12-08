package com.group10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;

public class Menu {
    private static final int MAX_COLS_OF_LEVEL_SELECT = 3;
    
    public static void mainMenu() {
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));
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
    
            Button button = new Button(filename);
            button.setOnAction(e -> {
                String levelPath = "level/" + filename;
                loadLevel(levelPath);
            });
            grid.add(button, i % MAX_COLS_OF_LEVEL_SELECT , i / MAX_COLS_OF_LEVEL_SELECT);
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
//        Game game = new Game(level);
    }
    
    @FXML
    public void highScores(ActionEvent event) {
        System.out.println("High Scores");
    }
    
    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
