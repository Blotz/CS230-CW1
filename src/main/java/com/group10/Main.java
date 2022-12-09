package com.group10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas, respond to arrow key presses, use a tick method that is
 * called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Main extends Application {
	public static final int WIDTH = 1050;
	public static final int HEIGHT = 600;
	private static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Main.stage = primaryStage;
		Menu.profileMenu();
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void changeScene(Scene scene) {
		Main.getStage().setScene(scene);
		Main.getStage().show();
	}
	public static Scene getScene(String fxml) {
		Parent root = null;
		try {
			root = FXMLLoader.load(Main.class.getResource(fxml));
		} catch (Exception e) {
			System.err.println("Error loading FXML file: " + fxml);
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}