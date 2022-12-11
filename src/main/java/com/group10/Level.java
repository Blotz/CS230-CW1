package com.group10;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Loads information from level file format
 */
public class Level {
    // Final because we won't want to change the size of these arrays
    private final Tile[][] map;
    private final Entity[][] entityMap;
    private ArrayList<MoveableEntity> npcs = new ArrayList<MoveableEntity>();
    /*
    right so this is the new npcs arraylist
    basically its conna contain all the npcs and let us easily access them

    in the update code we can loop though this arraylist and call their respective move code

    im gonna be workin ongetting Exit working!
    yeah i saw, looks gd
    there are also  a couple other changes
    there is a new file called Color and file called Direction
    they are both enums which lets us standardise what a direction is  and all posible colours
    ive already changed the code to use them
    #bangin

     */
    private int time;
    public final int MAX_HEIGHT;
    public final int MAX_WIDTH;
    private boolean gameOver = false;
    private boolean win = false;
    private final int levelNumber;
    private static final String FILE_NOT_FOUND = " didn't resolve to a file";
    private static final String NO_WIDTH_HEIGHT = "Please include height and width at the top of the file";
    private static final String WIDTH_HEIGHT_AND_MORE = "Please only include height and width at the top of the file";
    private static final String TOO_FEW_ROWS = "Too few rows for the tile format";
    private static final String INVALID_TILE_STRING = "Incorrectly formatted tile string";
    private static final String TOO_FEW_COLUMNS = "Too few columns for the tile format";
    private static final String TOO_MANY_ROWS = "Too many rows for the tile format";
    private static final String TOO_MANY_COLUMNS = "Too many columns for the tile format";
    private static final String INVALID_TIME_FORMAT = "Number must be an integer";
    private static final String ENTITY_FORMAT_ERROR = "Entity should be in format '(x,y) Class'";
    private static final String ENTITY_POSITION_FORMAT_ERROR = "Entity position should be in format '(x,y)'";
    private static final String ENTITY_NOT_FOUND_ERROR = "Entity not found inside Entity Map";
    private static final String INVALID_ENTITY_NAME = "Entity name %s doesnt match any Classes";
    private static final String INVALID_COLOR = "Invalid color %s";
    private static final String INVALID_DIRECTION = "Invalid direction %s";
    private static final String ENTITY_FORMAT = "(%d,%d) %s%n";

    
    /**
     * Loads a level from a String
     * @param levelData the level to load
     * @param levelNumber the level number
     * @throws IllegalArgumentException if the level file is incorrectly formatted
     */
    public Level(String levelData, int levelNumber) {
        this.levelNumber = levelNumber;
        Scanner in = new Scanner(levelData);
    
        // Read first line and parse dimensions
        String posString = in.nextLine();
        Scanner pos = new Scanner(posString).useDelimiter(" ");
        try {
            MAX_HEIGHT = pos.nextInt();
            MAX_WIDTH = pos.nextInt();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(NO_WIDTH_HEIGHT);
        }
        if (pos.hasNextInt()) {
            pos.close();
            throw new IllegalArgumentException(WIDTH_HEIGHT_AND_MORE);
        }
        
        // Initialize our arrays
        map = new Tile[MAX_HEIGHT][MAX_WIDTH];
        entityMap = new Entity[MAX_HEIGHT][MAX_WIDTH];
    
        // Board
        for (int i = 0; i < MAX_HEIGHT; i++) {
            String rowString = in.nextLine();
            Scanner row = new Scanner(rowString).useDelimiter(" ");
            if (rowString.startsWith("(")) {
                throw new IllegalArgumentException(TOO_FEW_ROWS);
            }
            try {
                for (int j = 0; j < MAX_WIDTH; j++) {
                    String tileColors = row.next();
                    if (tileColors.length() != 4) {
                        throw new IllegalArgumentException(INVALID_TILE_STRING);
                    }
                    
                    map[i][j] = new Tile(
                      charToColor(tileColors.charAt(0)),
                      charToColor(tileColors.charAt(1)),
                      charToColor(tileColors.charAt(2)),
                      charToColor(tileColors.charAt(3)));
                }
            } catch (NoSuchElementException e) {  // Catch too little columns
                throw new IllegalArgumentException(TOO_FEW_COLUMNS);
            }
            // Test for too many columns
            if (row.hasNextLine()) {
                row.close();
                throw new IllegalArgumentException(TOO_MANY_COLUMNS);
            }
            row.close();
        }
        
        // Parse at least one entity
        while (in.hasNext()) {
            String entityString = in.nextLine();
            
            
            if (!entityString.startsWith("(")) {
                try {
                    time = Integer.parseInt(entityString);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException(INVALID_TIME_FORMAT);
                }
                in.close();
                return; // Finished
            }
            
            // Look at creature row
            Scanner creature = new Scanner(entityString).useDelimiter(" ");
            String creaturePos;
            String creatureName;
            try {
                creaturePos = creature.next();
                creatureName = creature.next();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException(ENTITY_FORMAT_ERROR);
            }
    
            creaturePos = creaturePos.substring(1, creaturePos.length() - 1);  // Trim starting and trailing "(" ")"
            Scanner creaturePosParser = new Scanner(creaturePos).useDelimiter(",");
    
            int creatureX;
            int creatureY;
            try {
                creatureX = creaturePosParser.nextInt();
                creatureY = creaturePosParser.nextInt();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException(ENTITY_POSITION_FORMAT_ERROR);
            }
            if (creaturePosParser.hasNextInt()) {
                creaturePosParser.close();
                throw new IllegalArgumentException(ENTITY_POSITION_FORMAT_ERROR);
            }
            creaturePosParser.close();
            
            // Save entity to map
            Direction direction;
            Color color;
            switch (creatureName) {
                case "Player":
                    int score = creature.nextInt();
                    Player player = new Player(score);
                    entityMap[creatureY][creatureX] = player;
                    break;
                case "FloorFollowingThief":
                    direction = stringToDirection(creature.next());
                    color = charToColor(creature.next().charAt(0));
                    FloorFollowingThief floorFollowingThief = new FloorFollowingThief(direction, color);
                    entityMap[creatureY][creatureX] = floorFollowingThief;
                    npcs.add(floorFollowingThief);
                    break;
                case "FlyingAssassin":
                    direction = stringToDirection(creature.next());
                    FlyingAssassin fa = new FlyingAssassin(direction);
                    entityMap[creatureY][creatureX] = fa;
                    npcs.add(fa);  // Add npc to list for later use
                    break;
                case "SmartThief":
                    SmartThief smartThief = new SmartThief();
                    entityMap[creatureY][creatureX] = smartThief;
                    npcs.add(smartThief);
                    break;
                case "Exit":
                    Exit exit = new Exit();
                    entityMap[creatureY][creatureX] = exit;
                    break;
                case "Cent":
                case "Dollar":
                case "Ruby":
                case "Diamond":
                    Loot loot = new Loot(creatureName);
                    entityMap[creatureY][creatureX] = loot;
                    break;
                case "Clock":
                    Clock clock = new Clock();
                    entityMap[creatureY][creatureX] = clock;
                    break;
                case "Bomb":
                    break;
                case "Switch":
                    color = charToColor(creature.next().charAt(0));
                    Switch sw = new Switch(color);
                    entityMap[creatureY][creatureX] = sw;
                    break;
                case "Gate":
                    color = charToColor(creature.next().charAt(0));
                    Gate gate = new Gate(color);
                    entityMap[creatureY][creatureX] = gate;
                    break;
                default:
                    throw new IllegalArgumentException(String.format(INVALID_ENTITY_NAME, creatureName));
            }
        }
    }
    
    /**
     * Loads a level from a file.
     * @param levelPath The path to the level file.
     * @return The level loaded from the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public static String loadLevel(String levelPath) throws FileNotFoundException {
        // Load file from resources path
        InputStream file = Level.class.getResourceAsStream(levelPath);
        // If the path is invalid, throw an error!
        if (file == null) {
            throw new FileNotFoundException(levelPath + FILE_NOT_FOUND);
        }
        // Load entire file
        Scanner in = new Scanner(file);
        in.useDelimiter("\\Z");
        String levelString = in.next();
        in.close();
        
        // Read file into
        return levelString;
    }
    
    /**
     * Saves a level to a String.
     * @return The level as a String.
     */
    public String saveLevel() {
        String levelString = "";
        levelString += String.valueOf(getLevelNumber()) + "\n";
        levelString += MAX_HEIGHT + " " + MAX_WIDTH + "\n";
        
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                levelString += map[i][j].toString();
                if (j != MAX_WIDTH - 1) {
                    levelString += " ";
                }
            }
            levelString += "\n";
        }
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (entityMap[i][j] != null) {
                    levelString += String.format(ENTITY_FORMAT, j, i, entityMap[i][j].toString());
                }
            }
        }
        levelString += time + "\n";
        return levelString;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }
    
    /**
     * Converts a character into a color
     * @param c The character to convert
     * @return The color corresponding to the character
     */
    private static Color charToColor(char c) {
        switch (Character.toLowerCase(c)) {
            case 'r':
                return Color.RED;
            case 'g':
                return Color.GREEN;
            case 'b':
                return Color.BLUE;
            case 'y':
                return Color.YELLOW;
            case 'c':
                return Color.CYAN;
            case 'm':
                return Color.MAGENTA;
            default:
                throw new IllegalArgumentException(String.format(INVALID_COLOR, c));
        }
    }
    
    /**
     * Converts a color into a character
     * @param c The color to convert
     * @return The character corresponding to the color
     */
    public static char colorToChar(Color c) {
        switch (c) {
            case RED:
                return 'R';
            case BLUE:
                return 'B';
            case GREEN:
                return 'G';
            case YELLOW:
                return 'Y';
            case MAGENTA:
                return 'M';
            case CYAN:
                return 'C';
            default:
                    throw new IllegalArgumentException(String.format(INVALID_COLOR, c));
        }
    }
    
    /**
     * Converts a string into a direction
     * @param s The string to convert
     * @return The direction corresponding to the string
     */
    private static Direction stringToDirection(String s) {
        switch (s.toUpperCase()) {
            case "UP":
                return Direction.UP;
            case "DOWN":
                return Direction.DOWN;
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
            default:
                throw new IllegalArgumentException(String.format(INVALID_DIRECTION, s));
        }
    }
    
    /**
     * Converts a direction into a string
     * @param d The direction to convert
     * @return The string corresponding to the direction
     */
    public static String directionToString(Direction d) {
        switch (d) {
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case LEFT:
                return "LEFT";
            case RIGHT:
                return "RIGHT";
            default:
                throw new IllegalArgumentException(String.format(INVALID_DIRECTION, d));
        }
    }
    
    /**
     * Checks whether there is Loot on the map
     * @return True if there is loot, false otherwise
     */
    public boolean isLootOnMap() {
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (entityMap[i][j] instanceof Loot) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks whether there is a Switch on the map
     * @return True if there is a switch, false otherwise
     */
    public boolean isSwitchOnMap() {
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (entityMap[i][j] instanceof Switch) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getTime() {
        return time;
    }

    public int setTime(int t) {
        time = t;
        return time;
    }
    
    
    /**
     * gets the position of an entity
     * @param entity The entity to check
     * @return The position of the entity
     * @throws IllegalArgumentException If the entity is not on the map
     */
    public int[] getEntityPosition(Entity entity) {
        for (int y=0; y<MAX_HEIGHT; y++) {
            for (int x = 0; x < MAX_WIDTH; x++) {
                if (entity.equals(entityMap[y][x])) {
                    return new int[]{x,y};
                }
            }
        }
        throw new IllegalArgumentException(ENTITY_NOT_FOUND_ERROR);
    }
    
    /**
     * Get the entity at a given position
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The entity at the given position
     */
    public Entity getEntity(int x, int y) {
        return entityMap[y][x];
    }

    public void setEntity(Entity entity, int x, int y) {
        entityMap[y][x] = entity;
    }
    public Tile getTile(int x, int y) {
        return map[y][x];
    }
    
    /**
     * Get the Plauer object
     * @return The Player object
     */
    public Player getPlayer() {
        // Search entityMap for player
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                if (entityMap[i][j] instanceof Player) {
                    return (Player) entityMap[i][j];
                }
            }
        }
        throw new RuntimeException("Player not found");
    }
    
    /**
     * gets the colors of the tiles that an entity is on
     * @param entity The entity to check
     * @return The colors of the tiles that the entity is on
     */
    public Color[] getTileColorEntity(Entity entity) {
        for (int row = 0; row < entityMap.length; row++) {
            for (int col = 0; col < entityMap[row].length; col++) {
                Entity arrayEntity = entityMap[row][col];
                if (entity.equals(arrayEntity)) {
                    return map[row][col].getColors();
                }
            }
        }
        return null;
    }
    
    /**
     * Return tileColour using getColors from tile.java
     * @param x the X coordinate of a given tile
     * @param y the Y coordinate of a given tile
     * @return Char[] of max length 4
     */
    public Color[] getTileColor(Integer x, Integer y) {
        return map[y][x].getColors();
    }

    public Entity getEntity(Integer x, Integer y) {
        return entityMap[y][x];
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public boolean getGameOver() {
        return gameOver;
    }
    public boolean getWin() {
        return win;
    }
    
    
    /**
     * Moves an entity in a given direction
     * @param oldX The old x coordinate
     * @param oldY The old y coordinate
     * @param newX The new x coordinate
     * @param newY The new y coordinate
     */
    public void moveEntity(int oldX,  int oldY, int newX, int newY) {
        Entity movingEntity = entityMap[oldY][oldX];
        Entity targetEntity = entityMap[newY][newX];
        // Lovely. just working on all the move interactions. ive decided to jsut code it here

        if (movingEntity instanceof Player) {
            if (targetEntity instanceof Exit) {
                // Winn condidtion
                if (!isLootOnMap() && !isSwitchOnMap()) {
                    setGameOver(true);
                    setWin(true);
                    // move
                    entityMap[newY][newX] = movingEntity;
                    entityMap[oldY][oldX] = null;
                }
            } else if (targetEntity instanceof PickUp) {
                PickUp item = (PickUp) targetEntity;

                if (item instanceof Switch) {
                    ((Switch) item).onInteract(this);
                }

                // Checks if item is bomb
                if (item instanceof Bomb) {
                    // Starts the timer for the bomb
                    ((Bomb) item).startBomb(3);
                }

                if (item instanceof Clock) {
                    ((Clock) item).onInteract(movingEntity, this);
                }
                if (targetEntity instanceof Loot) {
                    ((Loot) targetEntity).onInteract(movingEntity, this);
                }

                // move
                entityMap[newY][newX] = movingEntity;
                entityMap[oldY][oldX] = null;
            } else if (targetEntity instanceof FlyingAssassin) {
                setGameOver(true);
                setWin(false);
            }
        } else if (movingEntity instanceof SmartThief || movingEntity instanceof FloorFollowingThief) {
            if (targetEntity instanceof Exit) {
                if (!isLootOnMap() && !isSwitchOnMap()) {
                    setGameOver(true);
                    setWin(false);
                }
            } else if (targetEntity instanceof Loot) {
                // move
                entityMap[newY][newX] = movingEntity;
                entityMap[oldY][oldX] = null;
            } else if (targetEntity instanceof Switch) {
                ((Switch) targetEntity).onInteract(movingEntity, this);
                // move
                entityMap[newY][newX] = movingEntity;
                entityMap[oldY][oldX] = null;
            }
        } else if (movingEntity instanceof FlyingAssassin) {
            if (targetEntity instanceof Player) {
                setGameOver(true);
                setWin(false);
            }
        }
        if (targetEntity != null) {
            // Trying to move into Entity
            // dont move
            return;
        }
        // move
        entityMap[newY][newX] = movingEntity;
        entityMap[oldY][oldX] = targetEntity;
    }
    
    
    /**
     * Calculates the next frame of game logic
     */
    public void update() {
        for(Entity entity : npcs) {
            if (entity instanceof FlyingAssassin) {
                FlyingAssassin flyingAssassin = (FlyingAssassin) entity;
                int[] oldPos = getEntityPosition(flyingAssassin);
                int[] newPos = flyingAssassin.move(this);

                moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
            } else if (entity instanceof SmartThief) {
                SmartThief smartThief = (SmartThief) entity;
                int[] oldPos = getEntityPosition(smartThief);
                int[] newPos = smartThief.move(this);

                moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
            } else if (entity instanceof FloorFollowingThief) {
                FloorFollowingThief floorFollowingThief = (FloorFollowingThief) entity;
                int[] oldPos = getEntityPosition(floorFollowingThief);
                int[] newPos = floorFollowingThief.move(this);

                moveEntity(oldPos[0], oldPos[1], newPos[0], newPos[1]);
            }

            // Bomb timer code
            else if (entity instanceof Bomb) {
                Bomb bomb = (Bomb) entity;
                // If the bombs timer has already begun, then countdown
                if (bomb.hasTimerBegun() == true) {
                    if (bomb.getTimer() > 0) {
                        bomb.countdownTimer(1);
                    }
                    else if (bomb.getTimer() == 0) {
                        bomb.explosion(this);
                    }
                }
            }
        }
        time--;
        if (time == 0) {
            setGameOver(true);
            setWin(false);
        }
    }
    
    public double getWidth() {
        return MAX_WIDTH;
    }
    public double getHeight() {
        return MAX_HEIGHT;
    }
}
