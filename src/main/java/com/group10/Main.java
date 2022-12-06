package com.group10;

import javafx.application.Application;
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

	@Override
	public void start(Stage primaryStage) throws Exception{
		Menu menu = new Menu(primaryStage);
		menu.mainMenu();
	}

	public static void main(String[] args) {
		launch(args);
	}
}