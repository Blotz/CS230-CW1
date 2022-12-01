package com.group10;

public abstract class MoveableEntity implements Entity {
    public int[] moveRight(Level level, int x, int y) {
        char[] colorsOnCurTile = level.getEntityTileColor(this);
//        int new_x = x;
//        while (new_x <= level.MAX_WIDTH) {
//            new_x++;
//
//        }
        // Better code to do for loop rather than while
        for (int new_x = x; new_x < level.MAX_WIDTH ; new_x++) {
            char[] colorsOnNewTile = level.getTileColor(new_x, y);
            for (char newColor: colorsOnNewTile) {
                for (char curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{new_x, y};
                    }
                }
            }
        }
        return new int[]{x, y};
    }
    
    public int[] moveLeft(Level level, int x, int y) {
        char[] colorsOnCurTile = level.getEntityTileColor(this);

        for (int new_x = x; new_x >= 0 ; new_x--) {
            char[] colorsOnNewTile = level.getTileColor(new_x, y);
            for (char newColor: colorsOnNewTile) {
                for (char curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{new_x, y};
                    }
                }
            }
        }
        return new int[]{x, y};
    }
    
    public int[] moveDown(Level level, int x, int y) {
        char[] colorsOnCurTile = level.getEntityTileColor(this);

        for (int new_y = x; new_y < level.MAX_HEIGHT ; new_y++) {
            char[] colorsOnNewTile = level.getTileColor(x, new_y);
            for (char newColor: colorsOnNewTile) {
                for (char curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{x, new_y};
                    }
                }
            }
        }
        return new int[]{x, y};
    }
    
    public int[] moveUp(Level level, int x, int y) {
        char[] colorsOnCurTile = level.getEntityTileColor(this);
        
        for (int new_y = x; new_y >= 0; new_y--) {
            char[] colorsOnNewTile = level.getTileColor(x, new_y);
            for (char newColor: colorsOnNewTile) {
                for (char curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{x, new_y};
                    }
                }
            }
        }
        return new int[]{x, y};
    }
}
