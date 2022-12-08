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
        
        int MAX_COLS = 3;
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
        
            });
            grid.add(button, i % MAX_COLS , i / MAX_COLS);
    
    
        }
        for (File file : files) {
        
        }
        changeScene(scene);
    }
    
    private void loadLevel(String name) {
    }
    
    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
