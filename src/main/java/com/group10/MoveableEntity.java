package com.group10;

public abstract class MoveableEntity implements Entity {
    
    public int[] moveRight(Level level, int x, int y) {

        int[] newPosition = new int[]{x, y};
        Color[] colorsOnCurTile = level.getTileColorEntity(this);
        
        // Better code to do for loop rather than while
        for (int new_x = x+1; new_x < level.MAX_WIDTH ; new_x++) {
            Color[] colorsOnNewTile = level.getTileColor(new_x, y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        newPosition[0] = new_x;
                        return newPosition;
                    }
                }
            }
        }
        return newPosition;
    }
    
    public int[] moveLeft(Level level, int x, int y) {
        int[] newPosition = new int[]{x, y};
        Color[] colorsOnCurTile = level.getTileColorEntity(this);

        for (int new_x = x-1; new_x >= 0 ; new_x--) {
            Color[] colorsOnNewTile = level.getTileColor(new_x, y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        newPosition[0] = new_x;
                        return newPosition;
                    }
                }
            }
        }
        return newPosition;
    }
    
    public int[] moveDown(Level level, int x, int y) {
        int[] newPosition = new int[]{x, y};
        Color[] colorsOnCurTile = level.getTileColorEntity(this);

        for (int new_y = y+1; new_y < level.MAX_HEIGHT ; new_y++) {
            Color[] colorsOnNewTile = level.getTileColor(x, new_y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        newPosition[1] = new_y;
                        return newPosition;
                    }
                }
            }
        }
        return newPosition;
    }
    
    public int[] moveUp(Level level, int x, int y) {
        int[] newPosition = new int[]{x, y};
        Color[] colorsOnCurTile = level.getTileColorEntity(this);
        
        for (int new_y = y-1; new_y >= 0; new_y--) {
            Color[] colorsOnNewTile = level.getTileColor(x, new_y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        newPosition[1] = new_y;
                        return newPosition;
                    }
                }
            }
        }
        return newPosition;
    }
}
