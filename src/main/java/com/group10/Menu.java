package com.group10;

import java.io.IOException;
import java.io.InputStream;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class Menu {
    Stage stage;
    
    private static final String BACKGROUND_IMAGE = "GUI/BackGround.jpg";
    private static final String IMAGE_NOT_FOUND = "Image '%s' not found";
    
    public Menu(Stage stage) {
        this.stage = stage;
    }
    
    public void mainMenu() {
        Pane root = new Pane();
        root.setPrefSize(Main.WIDTH, Main.HEIGHT);

        // Load background image
        InputStream is = Menu.class.getResourceAsStream(BACKGROUND_IMAGE);
        // Catch errors if the image is not found
        if (is == null) {
            throw new RuntimeException(String.format(IMAGE_NOT_FOUND, BACKGROUND_IMAGE));
        }
        
        // Create and add the background image
        ImageView img = new ImageView(new Image(is));
        img.setFitWidth(Main.WIDTH);
        img.setFitHeight(Main.HEIGHT);
        root.getChildren().add(img);
        
        // Create and add the title
        StackPane title = createTitle ("Cave game");
        title.setTranslateX(50);
        title.setTranslateY(200);

        // MOTD
        Text motd = new Text();
        motd.setFill(Color.WHITE);
        motd.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        motd.setText(MOTDRetriever.getMOTD());
        motd.setX(500);
        motd.setY(500);
        motd.setWrappingWidth(550);

        // TODO: Clean up code from here to remove the weird classes. use my code from createTitle as an example
        // Create and add the buttons
        MenuBox vbox = new MenuBox(new MenuItem("Level"));
        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        MenuBox scoreBoard = new MenuBox(
            new MenuItem("Highscores"));
        scoreBoard.setTranslateX(100);
        scoreBoard.setTranslateY(345);

        MenuBox loadFile = new MenuBox(
            new MenuItem("Load"));
        loadFile.setTranslateX(100);
        loadFile.setTranslateY(390);
 
        MenuBox exit = new MenuBox(
            new MenuItem("Exit"));
        exit.setTranslateX(100);
        exit.setTranslateY(435);

        root.getChildren().addAll(title,vbox,scoreBoard,loadFile,exit,motd);

        // Create Scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LevelSelect() {
        // Loop though levels and add them to the menu
        /* TODO:
         * I wanna build out a submenu for selecting Levels, I havent starting working on it yet tho sozz
         *
         */
    }
    
    private static StackPane createTitle(String name) {
        StackPane titleRoot = new StackPane();
        
        Rectangle bg = new Rectangle(375, 60);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(2);
        bg.setFill(null);
    
        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));
    
        titleRoot.setAlignment(Pos.CENTER);
        titleRoot.getChildren().addAll(bg, text);
        
        return titleRoot;
    }

    
    private class MenuBox extends VBox{
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

    private class MenuItem extends StackPane{
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
            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
            });
            setOnMouseClicked(event -> {
                /* TODO: turn this into a normal method.
                 * This way, we can use the stage attribute to build this submenu in the same screen
                 * Same thing goes for calling the Game. if you pass though the primaryStage, the game can be displayed
                 * in the same view port.
                 */
                if (name == "Level"){
                    Game.display();
                    //levelSelect();
                } else if (name == "Highscore"){

                } else if (name == "Profile"){

                } else if (name == "Exit"){
                    System.exit(0);
                }
            });
            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });

        }
    }
}