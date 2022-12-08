package com.group10;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

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
        // Add a bunch of filler text
        
        for (int i = 0; i < grid.getRowCount(); i++) {
            for (int j = 0; j < grid.getColumnCount(); j++) {
                grid.add(new javafx.scene.control.Label("Level " + (i * 10 + j)), j, i);
            }
        }
        changeScene(scene);
    }
    
    @FXML
    public void settings(ActionEvent event) {
        System.out.println("Settings");
    }
    
    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
