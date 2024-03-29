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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static javafx.scene.input.KeyCode.F;

/**
 * The main Game loop.
 */
public class Game {
    private static Level level = null;
    private static Player player;
    private static Scene scene;
    private static Timeline tickTimeline;
    private static final int GRID_SIZE = 50;
    private static double SCALE = 1.0;
    private static Image playerImage;
    private static Image flyassImage;
    private static Image smartthiefImage;
    private static Image floorthiefImage;

    private static Image exitImage;

    private static Image clockImage;

    private static Image rubyImage;
    private static Image diamondImage;
    private static Image dollarImage;
    private static Image centImage;

    private static Image bombImage;

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
    
    /**
     * Sets up all the data required for the level.
     * @param level the level to be loaded
     */
    public static void setLevel(Level level) {
        Game.level = level;
        Game.player = Game.level.getPlayer();
        Game.scene = Main.getScene("GUI/level.fxml");
        Parent root = Game.scene.getRoot();
        
        Canvas canvas = (Canvas) root.lookup(".canvas");
        canvas.setWidth(Game.level.getWidth() * GRID_SIZE);
        canvas.setHeight(Game.level.getHeight() * GRID_SIZE);
       
        
        Game.scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
        Game.tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
        Game.tickTimeline.setCycleCount(Animation.INDEFINITE);
        
        // Load images
        playerImage = new Image(Game.class.getResource("images/entity/player.png").toString());
        flyassImage = new Image(Game.class.getResource("images/entity/flyingassassin.png").toString());
        smartthiefImage = new Image(Game.class.getResource("images/entity/smartthief.png").toString());
        floorthiefImage = new Image(Game.class.getResource("images/entity/floorfollowingthief.png").toString());

        exitImage = new Image(Game.class.getResource("images/entity/exit.png").toString());

        clockImage = new Image(Game.class.getResource("images/entity/clock.png").toString());

        rubyImage = new Image(Game.class.getResource("images/entity/ruby.png").toString());
        diamondImage = new Image(Game.class.getResource("images/entity/diamond.png").toString());
        dollarImage = new Image(Game.class.getResource("images/entity/dollar.png").toString());
        centImage = new Image(Game.class.getResource("images/entity/cent.png").toString());

        bombImage = new Image((Game.class).getResource("images/entity/bomb.png").toString());

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
        tickTimeline.play();
        Main.changeScene(scene);
    }
    
    /**
     * Draws the level onto the scene obj's canvas
     */
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
    
    /**
     * Draws an entity onto the canvas
     * @param gc the graphics context of the canvas
     * @param entity the entity to be drawn
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     */
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
        } else if (entity instanceof Clock) {
            gc.drawImage(clockImage, x * GRID_SIZE, y * GRID_SIZE);
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
        } else if (entity instanceof Bomb) {
           gc.drawImage(bombImage, x * GRID_SIZE, y * GRID_SIZE);
        }

    }
    
    /**
     * Calculates the next frame of the game
     */
    private static void tick() {
        // Update the level
         level.update(); // that was commented out
        if (level.getGameOver()) {
            System.out.println("Game Over");
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
        Parent root = scene.getRoot();
        Text time = (Text) root.lookup(".title");
        time.setText("Time: " + level.getTime() / 2);
        drawLevel();
    }
    
    /**
     * Converts a color to an image
     * @param tiles the colors to be converted
     * @return the images of the colors
     */
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
    
    /**
     * Handles the key presses
     * @param event the key event
     */
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
            case P:
                pauseGame();
                break;
            case F:
                Main.toggleFullScreen(F);
            default:
                System.out.println("Unknown key pressed: " + event.getCode());
        }
        drawLevel();
        event.consume();
    }
    
    /**
     * Moves the player right
     */
    private static void moveRight() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveRight(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    /**
     * Moves the player left
     */
    private static void moveLeft() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveLeft(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    /**
     * Moves the player up
     */
    private static void moveUp() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveUp(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    /**
     * Moves the player down
     */
    private static void moveDown() {
        int[] oldPos = level.getEntityPosition(player);
        int[] newPos = player.moveDown(level,oldPos[0], oldPos[1]);
        level.moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
    }
    
    @FXML
    public void Pause(ActionEvent event) {
        pauseGame();
    }
    
    /**
     * Pauses the game
     */
    public static void pauseGame() {
        tickTimeline.pause();
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
        System.out.println("Menue");
        Main.changeScene(Main.getScene("GUI/mainMenu.fxml"));

    }

    /**
     * Generates the win screen
     * and updates the profile and highscore
     */
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
