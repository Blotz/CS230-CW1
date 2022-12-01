package com.group10;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
	private Parent createContent() {
		Pane root = new Pane();

		root.setPrefSize(1050, 600);

		try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/com/group10/BACKROUND.jpg"))){
			ImageView img = new ImageView(new Image(is));
			img.setFitWidth(1050);
			img.setFitHeight(600);
			root.getChildren().add(img);
		}
		catch(IOException e) {
			System.out.println("Couldn't load image");
		}

		Title title = new Title ("Cave game");
		title.setTranslateX(50);
		title.setTranslateY(200);

		MenuBox vbox = new MenuBox(new MenuItem("Level"));
		vbox.setTranslateX(100);
		vbox.setTranslateY(300);

		root.getChildren().addAll(title,vbox);

		return root;

	}
	@Override
	public void start(Stage primaryStage) throws Exception{
		Scene scene = new Scene(createContent());
		primaryStage.setTitle("Cave Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private static class Title extends StackPane {
		public Title(String name) {
			Rectangle bg = new Rectangle(375, 60);
			bg.setStroke(Color.WHITE);
			bg.setStrokeWidth(2);
			bg.setFill(null);

			Text text = new Text(name);
			text.setFill(Color.WHITE);
			text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

			setAlignment(Pos.CENTER);
			getChildren().addAll(bg,text);
		}
	}

	private static class MenuBox extends VBox {
		public MenuBox(MenuItem...items) {
			getChildren().add(createSeperator());

			for(MenuItem item : items) {
				getChildren().addAll(item, createSeperator());
			}
		}

		private Line createSeperator() {
			Line sep = new Line();
			sep.setEndX(210);
			sep.setStroke(Color.DARKGREY);
			return sep;
		}

	}


	private static class MenuItem extends StackPane{
		public MenuItem(String name) {
			LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
					new Stop(0, Color.DARKBLUE),
					new Stop(0.1, Color.BLACK),
					new Stop(0.9, Color.BLACK),
					new Stop(1, Color.DARKBLUE)

			});

			Rectangle bg = new Rectangle(200,30);
			bg.setOpacity(0.4);

			Text text = new Text(name);
			text.setFill(Color.DARKGREY);
			text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));

			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);
			setOnMouseEntered(event -> {
				bg.setFill(gradient);
				text.setFill(Color.WHITE);

			});

			setOnMouseExited(event -> {
				bg.setFill(Color.BLACK);
				text.setFill(Color.DARKGREY);
			});
			setOnMousePressed(event -> Game.display());

			setOnMouseReleased(event -> {
				bg.setFill(gradient);
			});

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}