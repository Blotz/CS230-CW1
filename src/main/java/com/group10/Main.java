package com.group10;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 600;
	private static final int CANVAS_HEIGHT = 400;

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 12;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;
		
	// Loaded images
	private Image playerImage;
	private Image dirtImage;
	private Image iconImage;
	
	// X and Y coordinate of player on the grid.
	private int playerX = 0;
	private int playerY = 0;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline; 
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background.
		String url = Main.class.getResource("player.png").toString();
		playerImage = new Image(url);
		url = String.valueOf(Main.class.getResource("dirt.png"));
		dirtImage = new Image(url);
		url = String.valueOf(Main.class.getResource("icon.png"));
		iconImage = new Image(url);


		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static Pane buildGUI() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane root = new BorderPane();
		// Create a toolbar with some nice padding and spacing
		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(toolbar);

		try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/com/group10/BACKROUND.jpg"))){
			ImageView img = new ImageView(new Image(is));
			img.setFitWidth(1050);
			img.setFitHeight(600);
			//root.getChildren().add(img);
		}
		catch(IOException e) {
			System.out.println("Couldn't load image");
		}

		//Add a input feature to display motd and leaderboard
		Label title = new Label("Menu");
		Label motd = new Label("Message of the day");
		Label leaderboard = new Label("LeaderBoard");

		Button game = new Button("Go to Level");
		game.setOnAction(e -> Game.display());

		// We add both buttons at the same time to the timeline (we could have done this in two steps).
		toolbar.getChildren().addAll(title, game, motd, leaderboard);

		// Finally, return the border pane we built up.
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}
}