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
    
    
    public Level(String levelPath) throws FileNotFoundException {
        
        // Load file from resources path
        InputStream file = Level.class.getResourceAsStream(levelPath);
        // If the path is invalid, throw an error!
        if (file == null) {
            System.err.println("File not found!");
            throw new FileNotFoundException(levelPath + " didn't resolve to a file");
        }
        Scanner in = new Scanner(file);
    
        // Read first line and parse dimensions
        String posString = in.nextLine();
        Scanner pos = new Scanner(posString).useDelimiter(" ");
        try {
            MAX_HEIGHT = pos.nextInt();
            MAX_WIDTH = pos.nextInt();
        } catch (NoSuchElementException e) {
            System.err.println("Please include height and width at the top of the file");
            throw e;
        }
        if (pos.hasNextInt()) {
            pos.close();
            System.err.println("Please only include height and width at the top of the file");
            throw new IllegalArgumentException();
        }
        
        // Initialize our arrays
        map = new Tile[MAX_HEIGHT][MAX_WIDTH];
        entityMap = new Entity[MAX_HEIGHT][MAX_WIDTH];
    
        // Board
        for (int i = 0; i < MAX_HEIGHT; i++) {
            String rowString = in.nextLine();
            Scanner row = new Scanner(rowString).useDelimiter(" ");
            if (rowString.startsWith("(")) {
                System.err.println("Too few rows for the tile format");
                throw new IllegalArgumentException();
            }
            try {
                for (int j = 0; j < MAX_WIDTH; j++) {
                    String tileColors = row.next();
                    if (tileColors.length() != 4) {
                        System.err.println("Incorrectly formatted tile string");
                        throw new IllegalArgumentException("Tile needs to be in the format XXXX");
                    }
                    map[i][j] = new Tile(tileColors.charAt(0), tileColors.charAt(1), tileColors.charAt(2), tileColors.charAt(3));
                }
            } catch (NoSuchElementException e) {  // Catch too little columns
                System.err.println("Too little columns for the tile format");
                throw e;
            }
            // Test for too many columns
            if (row.hasNextLine()) {
                row.close();
                System.err.println("Too many columns for the tile format");
                throw new IllegalArgumentException();
            }
            row.close();
        }
    
        // Check to see if we even have any entities
        String entityString = in.nextLine();
        if (!entityString.startsWith("(")) {
            try {
                time = Integer.parseInt(entityString);
            } catch (NumberFormatException e) {
                // if it's not the time or an entity, it must be an condition where too many
                // rows from tile setup
                System.err.println("Too many rows for the tile format");
                throw new IllegalArgumentException();
            }
            in.close();
            return; // Finished
        }
        
        // Parse at least one entity
        while (in.hasNext()) {
            if (!entityString.startsWith("(")) {
                try {
                    time = Integer.parseInt(entityString);
                } catch (NumberFormatException e) {
                    System.err.println("Number must be an integer");
                    throw e;
                }
                in.close();
                return; // Finished
            }
            Scanner creature = new Scanner(entityString).useDelimiter(" ");
            String creaturePos;
            String creatureName;
            try {
                creaturePos = creature.next();
                creatureName = creature.next();
            } catch (NoSuchElementException e) {
                System.err.println("Entity should be in format '(x,y) Class'");
                throw e;
            }
            if (creature.hasNext()) {
                System.err.println("Entity should be in format '(x,y) Class'");
                throw new IllegalArgumentException();
            }
    
            creaturePos = creaturePos.substring(1, creaturePos.length() - 1);  // Trim starting and trailing "(" ")"
            Scanner creaturePosParser = new Scanner(creaturePos).useDelimiter(",");
    
            int creatureX;
            int creatureY;
            try {
                creatureX = creaturePosParser.nextInt();
                creatureY = creaturePosParser.nextInt();
            } catch (NoSuchElementException e) {
                System.err.println("Entity position should be in the format '(x,y)'");
                throw e;
            }
            if (creaturePosParser.hasNextInt()) {
                creaturePosParser.close();
                System.err.println("Entity position should be in the format '(x,y)'");
                throw new IllegalArgumentException();
            }
            creaturePosParser.close();
            return;
            // Save entity to map
        }
    }
    
    public int getTime() {
        return time;
    }
    
    public char[] getTileColorEntity(Entity entity) {
        // TODO: remove placeholder code!
        return new char[]{'a','b','c'};
    }
    
    public char[] getTileColor(Integer x, Integer y) {
        return map[y][x].getColors();
    }
}
