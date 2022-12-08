package com.group10;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Game {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 400;

    // The width and height (in pixels) of each cell that makes up the game.
    private static final int GRID_CELL_WIDTH = 50;
    private static final int GRID_CELL_HEIGHT = 50;

    // The level object that makes up the gameboard and the Player
    private static Level level;
    private static Player player;

    // The width of the grid in number of cells.
    private static  int GRID_WIDTH = 12;

    private static  int GRID_HEIGHT = 12;

    // The canvas in the GUI. This needs to be a global variable
    // (in this setup) as we need to access it in different methods.
    // We could use FXML to place code in the controller instead.
    private static Canvas canvas;

    // Loaded images
    private static Image playerImage;
    private static Image flyassImage;
    private static Image dirtImage;
    private static Image iconImage;

    private static Image redTile;
    private static Image greenTile;
    private static Image blueTile;
    private static Image yellowTile;
    private static Image magentaTile;
    private static Image cyanTile;
    private static Image gridImage;

    // X and Y coordinate of player on the grid.
    private static int playerX = 0;
    private static int playerY = 0;

    // Time remaining and Text object to display
    // initiated here as we need to call it globally
    private static int levelTime = -1;
    private static Text timer = new Text();

    // Timeline which will cause tick method to be called periodically.
    private static Timeline tickTimeline;
    public static void display() {
        Stage primaryStage = new Stage();
        // Load images. Note we use png images with a transparent background.
        try {
            level = new Level("level/level1.txt"); // TODO: make this modular using a param selectedLevel
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        levelTime = level.getTime();

        //Player player = New Player(5,6);

        String url = String.valueOf(Game.class.getResource("images/newplayer.png"));
        playerImage = new Image(url);
        url = String.valueOf(Game.class.getResource("images/flyingassassin.png"));
        flyassImage = new Image(url);
        url = String.valueOf(Game.class.getResource("images/dirt.png"));
        dirtImage = new Image(url);
        url = String.valueOf(Game.class.getResource("images/icon.png"));
        iconImage = new Image(url);
        url = String.valueOf(Game.class.getResource("images/red.png"));
        redTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/green.png"));
        greenTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/blue.png"));
        blueTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/yellow.png"));
        yellowTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/magenta.png"));
        magentaTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/cyan.png"));
        cyanTile = new Image(url);
        url = String.valueOf(Game.class.getResource("images/grid.png"));
        gridImage = new Image(url);

        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Register an event handler for key presses.
        // This causes the processKeyEvent method to be called each time a key is pressed.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

        // Register a tick method to be called periodically.
        // Make a new timeline with one keyframe that triggers the tick method every half a second.
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> tick()));
        // Loop the timeline forever
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        // We start the timeline upon a button press.

        // Display the scene on the stage
        drawGame();
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    /**
     * Process a key event due to a key being pressed, e.g., to move the player.
     * @param event The key event that was pressed.
     */
    public static void processKeyEvent(KeyEvent event) {
        // We change the behaviour depending on the actual key that was pressed.
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
                moveRight();
                break;
            case LEFT:
                // Left key was pressed. So move the player right by one cell.
                moveLeft();
                break;
            case UP:
                // Up key was pressed. So move the player right by one cell.
                moveUp();
                break;
            case DOWN:
                // Down key was pressed. So move the player right by one cell.
                moveDown();
                break;
            default:
                // Do nothing for all other keys.
                break;
        }

        // Redraw game as the player may have moved.
        drawGame();

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    /**
     * Draw the game on the canvas.
     */
    public static void drawGame() {
        // Get the Graphic Context of the canvas. This is what we draw on.
    //    GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
   //     gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to gray.
     //   gc.setFill(Color.GRAY);
    //    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw row of dirt images
        // We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
        // We draw the row at y value 2.
    //    for (int x = 0; x < GRID_WIDTH; x++) {
     //       for (int y = 0; y < GRID_WIDTH; y++) {
    //        gc.drawImage(redTile, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
    //       }
    //    }

        // Draw player at current location
    //    gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);

        drawLevel(); //Change input form
    }

    public static void drawLevel() {
    
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int width = level.MAX_WIDTH;
        int height = level.MAX_HEIGHT;
        GRID_WIDTH = level.MAX_WIDTH;
        GRID_HEIGHT = level.MAX_HEIGHT;  // These variables wre final, change back when we change how level in inputted
        
        // Clear canvas
        gc.clearRect(0, 0, width*50, height*50);

        // Set the background to gray.
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, width*50, height*50);

        // Draw row of dirt images
        // We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
        // We draw the row at y value 2.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Image[] colours = getTileResource(level.getTileColor(x,y));

                int graphX = x * 50; // upscaled to match dimensions of the canvas
                int graphY = y * 50 +25;

                // Code for this is written below.
                if (colours[0] == dirtImage || colours[1] == dirtImage ||
                colours[2] == dirtImage || colours[3] == dirtImage){ // As dirt is 50x50 but tiles 25x25
                    gc.drawImage(dirtImage, graphX, graphX);
                } else {
                gc.drawImage(colours[0], graphX, graphY);
                gc.drawImage(colours[1], graphX+25, graphY);
                gc.drawImage(colours[2], graphX, graphY-25);
                gc.drawImage(colours[3], graphX+25, graphY-25);
                }
                gc.drawImage(gridImage, graphX, graphY-25);

                Entity entity = level.getEntity(x,y);

                if (entity != null){
                    if (Player.class.isInstance(entity)) {
                        player = (Player) entity;
                        gc.drawImage(playerImage, player.getX() * GRID_CELL_WIDTH, player.getY() * GRID_CELL_HEIGHT);
                    } else if (SmartThief.class.isInstance(entity)) {
                    } else if (FlyingAssassin.class.isInstance(entity)) {
                    } else if (FloorFollowingThief.class.isInstance(entity)) {
                    } else if (Gate.class.isInstance(entity)) {
                    } else if (Loot.class.isInstance(entity)) {
                    }



                }
            }
        }


        // Draw player at current location
        //gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
    }

    /**
     * getTileResource converts a char[] of tile colours
     * into a Image[] of image resources.
     * @param tiles The Char value of each tile.
     * @return An Image[] of the resource for each colour.
     */
    public static Image[] getTileResource(char[] tiles){
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

    /**
     * Reset the player's location and move them back to (0,0).
     */
    public static void resetPlayerLocation() {
        playerX = 0;
        playerY = 0;
        drawGame();
    }

    /**
     * Move the player to roughly the center of the grid.
     */
    public static void movePlayerToCenter() {
        // We just move the player to cell (5, 2)
        playerX = 5;
        playerY = 2;
        drawGame();
    }

    /**
     * This method is called periodically by the tick timeline
     * and would for, example move, perform logic in the game,
     * this might cause the bad guys to move (by e.g., looping
     * over them all and calling their own tick method).
     */
    public static void tick() {
        // Here we move the player right one cell and teleport
        // them back to the left side when they reach the right side.
        if (playerX >= level.MAX_WIDTH) {
            playerX = 0;
        } if (playerY >= level.MAX_HEIGHT){
            playerY = 0;
        }
        // For each tick we decrease the remaining time by 1
        // When time <= 0 a fail condition is met
        levelTime -= 1;
        if (levelTime <= 0) {

        } else {
            timer.setText("Time Remaining: " + levelTime);
        }
        // We then redraw the whole canvas.
        drawGame();
    }

    public static void moveRight() {
        // Here we move the player right one cell and teleport
        // them back to the left side when they reach the right side.
        playerX += 1;
        if (playerX > GRID_WIDTH-1) {
            playerX = 0;
        }
    }

    public static void moveLeft() {
        // Here we move the player left one cell and teleport
        // them back to the left side when they reach the right side.
        playerX -= 1;
        if (playerX < 0) {
            playerX = 0;
        }
    }

    public static void moveUp() {
        // Here we move the player left one cell and teleport
        // them back to the left side when they reach the right side.
        playerY -= 1;
        if (playerY < 0) {
            playerY = 0;
        }
    }

    public static void moveDown() {
        // Here we move the player left one cell and teleport
        // them back to the left side when they reach the right side.
        playerY += 1;
        if (playerY > GRID_HEIGHT-1) {
            playerY = 0;
        }
    }
    /**
     * React when an object is dragged onto the canvas.
     * @param event The drag event itself which contains data about the drag that occurred.
     */
    public static void canvasDragDroppedOccured(DragEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Print a string showing the location.
        String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
        System.out.println(s);

        // Draw an icon at the dropped location.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Draw the the image so the top-left corner is where we dropped.
        gc.drawImage(iconImage, x, y);
        // Draw the the image so the center is where we dropped.
        // gc.drawImage(iconImage, x - iconImage.getWidth() / 2.0, y - iconImage.getHeight() / 2.0);
    }

    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
    private static Pane buildGUI() {
        // Create top-level panel that will hold all GUI nodes.
        BorderPane root = new BorderPane();

        // Create the canvas that we will draw on.
        // We store this as a gloabl variable so other methods can access it.
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        // Create a toolbar with some nice padding and spacing
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(toolbar);

        // Create the toolbar content

        // Reset Player Location Button
        Button resetPlayerLocationButton = new Button("Reset Player");
        toolbar.getChildren().add(resetPlayerLocationButton);

        // Setup the behaviour of the button.
        resetPlayerLocationButton.setOnAction(e -> {
            // We keep this method short and use a method for the bulk of the work.
            resetPlayerLocation();
        });

        // Center Player Button
        Button centerPlayerLocationButton = new Button("Center Player");
        toolbar.getChildren().add(centerPlayerLocationButton);

        // Setup the behaviour of the button.
        centerPlayerLocationButton.setOnAction(e -> {
            // We keep this method short and use a method for the bulk of the work.
            movePlayerToCenter();
        });

        // Tick Timeline buttons
        Button startTickTimelineButton = new Button("Start Ticks");
        Button stopTickTimelineButton = new Button("Stop Ticks");
        // We add both buttons at the same time to the timeline (we could have done this in two steps).
        toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
        // Stop button is disabled by default
        stopTickTimelineButton.setDisable(true);

        // Setup the behaviour of the buttons.
        startTickTimelineButton.setOnAction(e -> {
            // Start the tick timeline and enable/disable buttons as appropriate.
            startTickTimelineButton.setDisable(true);
            tickTimeline.play();
            stopTickTimelineButton.setDisable(false);
        });

        stopTickTimelineButton.setOnAction(e -> {
            // Stop the tick timeline and enable/disable buttons as appropriate.
            stopTickTimelineButton.setDisable(true);
            tickTimeline.stop();
            startTickTimelineButton.setDisable(false);
        });

        // Setup a draggable image.
        ImageView draggableImage = new ImageView();
        draggableImage.setImage(iconImage);
        toolbar.getChildren().add(draggableImage);

        // This code setup what happens when the dragging starts on the image.
        // You probably don't need to change this (unless you wish to do more advanced things).
        draggableImage.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // Mark the drag as started.
                // We do not use the transfer mode (this can be used to indicate different forms
                // of drags operations, for example, moving files or copying files).
                Dragboard db = draggableImage.startDragAndDrop(TransferMode.ANY);

                // We have to put some content in the clipboard of the drag event.
                // We do not use this, but we could use it to store extra data if we wished.
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello");
                db.setContent(content);

                // Consume the event. This means we mark it as dealt with.
                event.consume();
            }
        });

        // This code allows the canvas to receive a dragged object within its bounds.
        // You probably don't need to change this (unless you wish to do more advanced things).
        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // Mark the drag as acceptable if the source was the draggable image.
                // (for example, we don't want to allow the user to drag things or files into our application)
                if (event.getGestureSource() == draggableImage) {
                    // Mark the drag event as acceptable by the canvas.
                    event.acceptTransferModes(TransferMode.ANY);
                    // Consume the event. This means we mark it as dealt with.
                    event.consume();
                }
            }
        });

        // This code allows the canvas to react to a dragged object when it is finally dropped.
        // You probably don't need to change this (unless you wish to do more advanced things).
        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // We call this method which is where the bulk of the behaviour takes place.
                canvasDragDroppedOccured(event);
                // Consume the event. This means we mark it as dealt with.
                event.consume();
            }
        });

        // Create text to display the remaining time in the level
        timer.setFill(Color.PURPLE);
        timer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        timer.setX(200);
        timer.setY(50);
        toolbar.getChildren().add(timer);

        // Finally, return the border pane we built up.
        return root;
    }
}
