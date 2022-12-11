package com.group10;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.Arrays;

import static com.group10.Color.RED;
import static java.lang.System.*;

public class Game {
    private static Level level = null;
    private static Player player;
    private static Scene scene;
    private static Timeline tickTimeline;
    private static final int GRID_SIZE = 50;
    
    private static Image playerImage;
    private static Image flyassImage;
    private static Image smartthiefImage;
    private static Image floorthiefImage;

    private static Image exitImage;

    private static Image rubyImage;
    private static Image diamondImage;
    private static Image dollarImage;
    private static Image centImage;

    private static Image redGateImage;
    private static Image blueGateImage;
    private static Image greenGateImage;
    private static Image redSwitchImage;
    private static Image greenSwitchImage;
    private static Image blueSwitchImage;

    private static Image dirtImage;
    private static Image redTile;
    private static Image greenTile;
    private static Image blueTile;
    private static Image yellowTile;
    private static Image magentaTile;
    private static Image cyanTile;
    private static Image gridImage;
    
    public static void setLevel(Level level) {
        Game.level = level;
        Game.player = Game.level.getPlayer();
        Game.scene = Main.getScene("GUI/level.fxml");
        Game.scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
        Game.tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
        Game.tickTimeline.setCycleCount(Animation.INDEFINITE);
        
        // Load images
        playerImage = new Image(Game.class.getResource("images/entity/player.png").toString());
        flyassImage = new Image(Game.class.getResource("images/entity/flyingassassin.png").toString());
        smartthiefImage = new Image(Game.class.getResource("images/entity/smartthief.png").toString());
        floorthiefImage = new Image(Game.class.getResource("images/entity/floorfollowingthief.png").toString());

        exitImage = new Image(Game.class.getResource("images/entity/exit.png").toString());

        rubyImage = new Image(Game.class.getResource("images/entity/ruby.png").toString());
        diamondImage = new Image(Game.class.getResource("images/entity/diamond.png").toString());
        dollarImage = new Image(Game.class.getResource("images/entity/dollar.png").toString());
        centImage = new Image(Game.class.getResource("images/entity/cent.png").toString());

        redGateImage = new Image(Game.class.getResource("images/entity/redGate.png").toString());
        blueGateImage = new Image(Game.class.getResource("images/entity/blueGate.png").toString());
        greenGateImage = new Image(Game.class.getResource("images/entity/greenGate.png").toString());
        redSwitchImage = new Image(Game.class.getResource("images/entity/redSwitch.png").toString());
        greenSwitchImage = new Image(Game.class.getResource("images/entity/greenSwitch.png").toString());
        blueSwitchImage = new Image(Game.class.getResource("images/entity/blueSwitch.png").toString());

        dirtImage = new Image(Game.class.getResource("images/dirt.png").toString());
        redTile = new Image(Game.class.getResource("images/red.png").toString());
        greenTile = new Image(Game.class.getResource("images/green.png").toString());
        blueTile = new Image(Game.class.getResource("images/blue.png").toString());
        yellowTile = new Image(Game.class.getResource("images/yellow.png").toString());
        magentaTile = new Image(Game.class.getResource("images/magenta.png").toString());
        cyanTile = new Image(Game.class.getResource("images/cyan.png").toString());
        gridImage = new Image(Game.class.getResource("images/grid.png").toString());
    }
    
    public static void start() {
        if (level == null) {
            throw new RuntimeException("Level not set");
        }
        drawLevel();
        Main.changeScene(scene);
    }
    
    private static void drawLevel() {
        // Get canvas
        Parent root = scene.getRoot();
        Canvas canvas = (Canvas) root.lookup(".canvas");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Clear canvas
        gc.clearRect(0, 0, level.MAX_WIDTH * GRID_SIZE, level.MAX_HEIGHT * GRID_SIZE);
        // Draw the level
        for (int x = 0; x < level.MAX_WIDTH; x++) {
            for (int y = 0; y < level.MAX_HEIGHT; y++) {
                Image[] colours = convertColorToImage(level.getTileColor(x,y));
                
                int graphX = x * GRID_SIZE;
                int graphY = y * GRID_SIZE + GRID_SIZE / 2;
                
               
                if (   colours[0] == dirtImage
                    || colours[1] == dirtImage
                    || colours[2] == dirtImage
                    || colours[3] == dirtImage) { // As dirt is 50x50 but tiles 25x25
                    gc.drawImage(dirtImage, graphX, graphX);
                } else {
                    gc.drawImage(colours[0], graphX, graphY);
                    gc.drawImage(colours[1], graphX+GRID_SIZE/2, graphY);
                    gc.drawImage(colours[2], graphX, graphY-GRID_SIZE/2);
                    gc.drawImage(colours[3], graphX+GRID_SIZE/2, graphY-GRID_SIZE/2);
                }

                gc.drawImage(gridImage, graphX, graphY-GRID_SIZE/2);
                // Draw the entities
                drawEntity(gc, level.getEntity(x, y), x, y);

            }
        }
    }
    private static void drawEntity(GraphicsContext gc, Entity entity, int x, int y) {
        // Guard
        if (entity == null) {
            return;
        }

        if (entity instanceof Player) {
            gc.drawImage(playerImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (entity instanceof FlyingAssassin) {
            gc.drawImage(flyassImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (entity instanceof SmartThief) {
             gc.drawImage(smartthiefImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (entity instanceof FloorFollowingThief) {
             gc.drawImage(floorthiefImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (entity instanceof Exit) {
             gc.drawImage(exitImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (entity instanceof Loot) {
            String gemName = ((Loot) entity).getGemName();
            switch (gemName) {
                case "Ruby":
                    gc.drawImage(rubyImage, x * GRID_SIZE, y * GRID_SIZE);
                    break;
                case "Diamond":
                    gc.drawImage(diamondImage, x * GRID_SIZE, y * GRID_SIZE);
                    break;
                case "Dollar":
                    gc.drawImage(dollarImage, x * GRID_SIZE, y * GRID_SIZE);
                    break;
                case "Cent":
                    gc.drawImage(centImage, x * GRID_SIZE, y * GRID_SIZE);
                    break;
            }
        } else if (entity instanceof Gate) {
            Color gateColor = ((Gate) entity).getColour();
            switch (gateColor) {
                case RED -> gc.drawImage(redGateImage, x * GRID_SIZE, y * GRID_SIZE);
                case GREEN -> gc.drawImage(greenGateImage, x * GRID_SIZE, y * GRID_SIZE);
                case BLUE -> gc.drawImage(blueGateImage, x * GRID_SIZE, y * GRID_SIZE);
            }
        } else if (entity instanceof Switch) {
            Color swColor = ((Switch) entity).getColour();
            switch (swColor) {
                case RED -> gc.drawImage(redSwitchImage, x * GRID_SIZE, y * GRID_SIZE);
                case GREEN -> gc.drawImage(greenSwitchImage, x * GRID_SIZE, y * GRID_SIZE);
                case BLUE -> gc.drawImage(blueSwitchImage, x * GRID_SIZE, y * GRID_SIZE);

            }
        }
    }
    
    private static void tick() {
        // Update the level
         level.update(); // that was commented out
        if (level.getGameOver()) {
            tickTimeline.stop();
            if (level.getWin()) {
                winScreen();
                return;
            } else {
                 Main.changeScene(Main.getScene("GUI/LossScreen.fxml"));
                 return;
            }
        }
        // Redraw the level
        drawLevel();
    }
    private static Image[] convertColorToImage(Color[] tiles) {
        Image[] tileImages = new Image[4];
        for (int i = 0; i<4; i++){
            switch(tiles[i]){
                case BLUE:
                    tileImages[i] = blueTile;
                    break;
                case RED:
                    tileImages[i] = redTile;
                    break;
                case YELLOW:
                    tileImages[i] = yellowTile;
                    break;
                case GREEN:
                    tileImages[i] = greenTile;
                    break;
                case CYAN:
                    tileImages[i] = cyanTile;
                    break;
                case MAGENTA:
                    tileImages[i] = magentaTile;
                    break;
                default:
                    tileImages[i] = dirtImage;
                    break;
            }
        }
        return tileImages;
    }
    
    private static void processKeyEvent(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case ESCAPE:
                Menu.mainMenu();
                break;
            default:
                out.println("Unknown key pressed: " + event.getCode());
        }
        drawLevel();
        event.consume();
    }
    
    private static void moveRight() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveRight(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    private static void moveLeft() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveLeft(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    private static void moveUp() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveUp(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    private static void moveDown() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveDown(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    
    @FXML
    public void resetPlayerLocation(ActionEvent event) {
        out.println("Resetting player location");
        // Reset the player location
        int[] oldPos = level.getEntityPosition(player);
        int[] start = {0, 0};
    
        level.moveEntity(oldPos[0], oldPos[1], start[0], start[1]);
        drawLevel();
    }
    
    @FXML
    public void movePlayerCenter(ActionEvent event) {
        out.println("Move player to center");
        // Move the player to the center of the level
        int[] oldPos = level.getEntityPosition(player);

        level.moveEntity(oldPos[0], oldPos[1], level.MAX_WIDTH / 2, level.MAX_HEIGHT / 2);
        drawLevel();
    }
    
    @FXML
    public void startTicks(ActionEvent event) {
        out.println("Starting ticks");
        // Start the ticks
        tickTimeline.play();
    }
    
    @FXML
    public void stopTicks(ActionEvent event) {
        out.println("Stopping ticks");
        // Stop the ticks
        tickTimeline.stop();
    }

    public void Pause(ActionEvent event) {
        tickTimeline.pause();
        out.println("Pause");
        Main.changeScene(Main.getScene("GUI/PauseMenue.fxml"));
    }

    @FXML
    public void Save(ActionEvent event){
        Profile.saveLevel(Profile.getProfileName(), level.saveLevel());
        System.out.println("Save");

    }

    @FXML
    public void Resume(ActionEvent event){
        tickTimeline.play();
        Main.changeScene(scene);
    }

    @FXML
    public void MMenue(ActionEvent event){
        out.println("Menue");
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));

    }

    private static void winScreen(){
        int levelNum = level.getLevelNumber();
        Profile.updateProfile(levelNum + 1);
        int score = player.getScore();
        Highscores.addHighscore(Profile.getProfileName(), levelNum, score);
        
        Scene scene = Main.getScene("GUI/WinScreen.fxml");
        Parent root = scene.getRoot();
        Text scoreText = (Text) root.lookup(".score");
        scoreText.setText(String.format("%s", score));
        Main.changeScene(scene);
    }
    
}
