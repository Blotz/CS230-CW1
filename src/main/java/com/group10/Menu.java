package com.group10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class Menu {
    private static final int MAX_COLS_OF_LEVEL_SELECT = 3;
    
    private static void changeScene(Scene scene) {
        Main.getStage().setScene(scene);
        Main.getStage().show();
    }
    private static Scene getScene(String fxml) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Menu.class.getResource(fxml));
        } catch (Exception e) {
            System.err.println("Error loading FXML file: " + fxml);
            e.printStackTrace();
        }
        return new Scene(root);
    }
    
    public static void mainMenu() {
        changeScene(getScene("GUI/mainMenu.fxml"));
    }
    
    @FXML
    public void levelSelect(ActionEvent event) {
        System.out.println("Level Select");
        Scene scene = getScene("GUI/levelSelect.fxml");
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
                System.out.println("Loading level: " + levelPath);
                loadLevel(levelPath);
            });
            grid.add(button, i % MAX_COLS_OF_LEVEL_SELECT , i / MAX_COLS_OF_LEVEL_SELECT);
        }
        changeScene(scene);
    }
    
    private static void loadLevel(String name) {
        System.out.println("Loading level: " + name);
        // Load the level
//        Level level = new Level(name);
//        // Load the level scene
//        Scene scene = getScene("GUI/level.fxml");
//        // Set the level in the controller
//        LevelController controller = (LevelController) scene.getUserData();
//        controller.setLevel(level);
//        // Change the scene
//        changeScene(scene);
    }
    
    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
