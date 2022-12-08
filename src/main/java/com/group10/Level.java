package com.group10;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Loads information from level file format
 */
public class Level {
    // Final because we won't want to change the size of these arrays
    private final Tile[][] map;
    private final Entity[][] entityMap;
    private int time;
    public final int MAX_HEIGHT;
    public final int MAX_WIDTH;
    
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
    private static final String INVALID_ENTITY_NAME = "Entity name %s doesnt match any Classes";
    
    
    public Level(String levelPath) throws FileNotFoundException {
        
        // Load file from resources path
        InputStream file = Level.class.getResourceAsStream(levelPath);
        // If the path is invalid, throw an error!
        if (file == null) {
            throw new FileNotFoundException(levelPath + FILE_NOT_FOUND);
        }
        Scanner in = new Scanner(file);
    
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
                    map[i][j] = new Tile(tileColors.charAt(0), tileColors.charAt(1), tileColors.charAt(2), tileColors.charAt(3));
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
            if (creature.hasNext()) {
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
            switch (creatureName) {
                case "Gate":
                    Gate entity = new Gate("red");
                    entityMap[creatureY][creatureX] = (Entity) entity;
                    break;
                case "Player":
                    Player player = new Player(creatureX, creatureY);
                    entityMap[creatureY][creatureX] = (Player) player;
                    break;
                case "FloorFollowingThief":
                    break;
                case "Ruby":
                    break;
                case "Diamond":
                    break;
                case "Door":
                    break;
                case "Clock":
                    break;
                default:
                    throw new IllegalArgumentException(String.format(INVALID_ENTITY_NAME, creatureName));
            }
        }
    }
    
    public int getTime() {
        return time;
    }
    
    public char[] getTileColorEntity(Entity entity) {
        char[] colours = null;
        for (int row = 0; row < entityMap.length; row++) {
            for (int col = 0; col < entityMap[row].length; col++) {
                Entity arrayEntity = entityMap[row][col];
                if (entity.equals(arrayEntity)) {
                    colours = map[row][col].getColors();
                }
            }
        }
        return colours;
    }
    
    /**
     * Return tileColour using getColors from tile.java
     * @param x the X coordinate of a given tile
     * @param y the Y coordinate of a given tile
     * @return Char[] of max length 4
     */
    public char[] getTileColor(Integer x, Integer y) {
        return map[y][x].getColors();
    }

    public Entity getEntity(Integer x, Integer y) {
        return entityMap[y][x];
    }

    public void moveEntity(int oldX, int oldY, int[] newP) {
       if (entityMap[newP[1]][newP[0]] == null) { // Switched as y and x swap in the classes
           Entity temp = entityMap[oldY][oldX];
           entityMap[newP[1]][newP[0]] = temp;
           entityMap[oldY][oldX] = null;
       }

    }

    public Player getPlayer() {
        Player player = null;
        for (int row = 0; row < entityMap.length; row++) {
            for (int col = 0; col < entityMap[row].length; col++) {
                Entity entity = entityMap[row][col];
               if (Player.class.isInstance(entity)){
                   player = (Player) entity;
               }
            }
        }
    return player;
    }
}
