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
import javafx.util.Duration;

public class Game {
    private static Level level = null;
    private static Player player;
    private static Scene scene;
    private static Timeline tickTimeline;
    private static final int GRID_SIZE = 50;
    
    private static Image playerImage;
    private static Image flyassImage;
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
        playerImage = new Image(Game.class.getResource("images/newplayer.png").toString());
        flyassImage = new Image(Game.class.getResource("images/flyingassassin.png").toString());
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
                Image[] colours = convertCharToImage(level.getTileColor(x,y));
                
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
        if (Player.class.isInstance(entity)) {
            gc.drawImage(playerImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (FlyingAssassin.class.isInstance(entity)) {
            gc.drawImage(flyassImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (SmartThief.class.isInstance(entity)) {
            // gc.drawImage(smartThiefImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (FloorFollowingThief.class.isInstance(entity)) {
            // gc.drawImage(floorThiefImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (Gate.class.isInstance(entity)) {
            // gc.drawImage(gateImage, x * GRID_SIZE, y * GRID_SIZE);
        } else if (Loot.class.isInstance(entity)) {
            Loot loot = (Loot) entity;
            if (loot.value == 10) {
                // gc.drawImage(rubyImage, x * GRID_SIZE, y * GRID_SIZE);
            } else if (loot.value == 20) {
                // gc.drawImage(emeraldImage, x * GRID_SIZE, y * GRID_SIZE);
            }
        }
        
        
    }
    
    private static void tick() {
        // Update the level
        // level.update();
        // Redraw the level
        drawLevel();
    }
    private static Image[] convertCharToImage(char[] tiles) {
        Image[] tileImages = new Image[4];
        for (int i = 0; i<4; i++){
            switch(tiles[i]){
                case 'B':
                    tileImages[i] = blueTile;
                    break;
                case 'R':
                    tileImages[i] = redTile;
                    break;
                case 'Y':
                    tileImages[i] = yellowTile;
                    break;
                case 'G':
                    tileImages[i] = greenTile;
                    break;
                case 'C':
                    tileImages[i] = cyanTile;
                    break;
                case 'M':
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
                System.out.println("Unknown key pressed: " + event.getCode());
        }
        drawLevel();
        event.consume();
    }
    
    private static void moveRight() {
        int x = player.getX(level);
        int y = player.getY(level);
        int[] pos = player.moveRight(level, x, y);
        level.moveEntity(x, y, pos[0], pos[1]);
    }
    private static void moveLeft() {
        int x = player.getX(level);
        int y = player.getY(level);
        int[] pos = player.moveLeft(level, x, y);
        level.moveEntity(x, y, pos[0], pos[1]);
    }
    private static void moveUp() {
        int x = player.getX(level);
        int y = player.getY(level);
        int[] pos = player.moveUp(level, x, y);
        level.moveEntity(x, y, pos[0], pos[1]);
    }
    private static void moveDown() {
        int x = player.getX(level);
        int y = player.getY(level);
        int[] pos = player.moveDown(level, x, y);
        level.moveEntity(x, y, pos[0], pos[1]);
    }
    
    @FXML
    public void resetPlayerLocation(ActionEvent event) {
        System.out.println("Resetting player location");
        // Reset the player location
        int x = player.getX(level);
        int y = player.getY(level);
        int[] start = {0, 0};
    
        level.moveEntity(x, y, start[0], start[1]);
        drawLevel();
    }
    
    @FXML
    public void movePlayerCenter(ActionEvent event) {
        System.out.println("Move player to center");
        // Move the player to the center of the level
        int x = player.getX(level);
        int y = player.getY(level);
        level.moveEntity(x, y, level.MAX_WIDTH / 2, level.MAX_HEIGHT / 2);
        drawLevel();
    }
    
    @FXML
    public void startTicks(ActionEvent event) {
        System.out.println("Starting ticks");
        // Start the ticks
        tickTimeline.play();
    }
    
    @FXML
    public void stopTicks(ActionEvent event) {
        System.out.println("Stopping ticks");
        // Stop the ticks
        tickTimeline.stop();
    }
    
}
