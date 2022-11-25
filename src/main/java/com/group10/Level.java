package com.group10;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Level {
    private Tile[][] map;
    
    public Level(String levelPath) {
        try {
            this.loadLevelFile(levelPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void loadLevelFile(String levelPath) throws FileNotFoundException {
        InputStream file = Level.class.getResourceAsStream(levelPath);
        Scanner in = new Scanner(file);
        
        String posString = in.nextLine();
        Scanner pos = new Scanner(posString).useDelimiter(" ");
        
        Integer height = pos.nextInt();
        Integer width = pos.nextInt();
        map = new Tile[height][width];
        
        // Board
        for (int i = 0; i < height; i++) {
            String rowString = in.nextLine();
            Scanner row = new Scanner(rowString).useDelimiter(" ");
            for (int j = 0; j < width; j++) {
                String tileColors = row.next();
                map[i][j] = new Tile(tileColors.charAt(0), tileColors.charAt(1), tileColors.charAt(2), tileColors.charAt(3));
            }
        }
        
        // Check each row to see if it starts with brackets :p
        while (in.hasNext()) {
            String creatureRow = in.nextLine();
            
            if (!creatureRow.startsWith("(")) {
                Integer time = Integer.parseInt(creatureRow);
                System.out.println("time: " + time);
                break;  // prolly can return at this point :pp
            }
            
            Scanner creature = new Scanner(creatureRow).useDelimiter(" ");
            String creaturePos = creature.next();
            String creatureName = creature.next();
            
            creaturePos = creaturePos.substring(1, creaturePos.length()-1);  // Trim starting and trailing "(" ")"
            Scanner creaturePosParser = new Scanner(creaturePos).useDelimiter(",");
            
            Integer creatureX = creaturePosParser.nextInt();
            Integer creatureY = creaturePosParser.nextInt();
            System.out.println(creatureName + ": " + creatureX + " " + creatureY);
        }
    }
}
