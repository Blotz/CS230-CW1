package com.group10;

import java.util.Objects;

/**
 * The Floor Following Thief is a type of thief that follows the floor colour in a clockwise direction.
 */
public class FloorFollowingThief extends MoveableEntity {
    private Direction initDirection;
    private Direction direction;
    private final Color colour;
    private static final String FORMAT = "FloorFollowingThief %s %s";

    public FloorFollowingThief(Direction initDirection, Color colour) {
        this.initDirection = initDirection;
        this.direction = initDirection;
        this.colour = colour;
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT, Level.directionToString(initDirection), Level.colorToChar(colour));
    }
    
    /**
     * Calculates the next direction the thief should move in
     * @return next position the thief should move to
     */
    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int[] newPos;
        Entity entity;

        // Turn left?
        newPos = switch (direction) {
            case UP -> moveLeft(level, pos[0], pos[1]);
            case DOWN -> moveRight(level, pos[0], pos[1]);
            case LEFT -> moveDown(level, pos[0], pos[1]);
            case RIGHT -> moveUp(level, pos[0], pos[1]);
        };
        
        entity = level.getEntity(newPos[0], newPos[1]);
        
        boolean canMove = newPos[0] != pos[0] ^ newPos[1] != pos[1];
        canMove = canMove && ((entity == null) || (entity instanceof Loot) || (entity instanceof Switch));
        if (canMove) {
            direction = switch (direction) {
                case UP -> Direction.LEFT;
                case DOWN -> Direction.RIGHT;
                case LEFT -> Direction.DOWN;
                case RIGHT -> Direction.UP;
            };
            return newPos;
        }
        // Move forward?
        newPos = switch (direction) {
            case UP -> moveUp(level, pos[0], pos[1]);
            case DOWN -> moveDown(level, pos[0], pos[1]);
            case LEFT -> moveLeft(level, pos[0], pos[1]);
            case RIGHT -> moveRight(level, pos[0], pos[1]);
        };
        entity = level.getEntity(newPos[0], newPos[1]);
        canMove = newPos[0] != pos[0] ^ newPos[1] != pos[1];
        canMove = canMove && ((entity == null) || (entity instanceof Loot) || (entity instanceof Switch));
        if (canMove) {
            return newPos;
        }
        // Turn right!
        this.direction = switch (direction) {
            case UP -> Direction.RIGHT;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            case RIGHT -> Direction.DOWN;
        };
        newPos = switch (direction) {
            case UP -> moveUp(level, pos[0], pos[1]);
            case DOWN -> moveDown(level, pos[0], pos[1]);
            case LEFT -> moveLeft(level, pos[0], pos[1]);
            case RIGHT -> moveRight(level, pos[0], pos[1]);
        };
//        System.out.println("direction: " + direction);
//        System.out.println("old pos: " + pos[0] + ", " + pos[1]);
//        System.out.println("newPos: " + newPos[0] + ", " + newPos[1]);
        return newPos;
    }
    
    /**
     * Calculates the position if the thief moves up
     * @param level the level the thief is on
     * @param x the x position of the thief
     * @param y the y position of the thief
     * @return the new position of the thief
     */
    @Override
    public int[] moveLeft(Level level, int x, int y) {
        if (x - 1 >= 0) {
            Color[] colorsOnNewTile = level.getTileColor(x - 1, y);
            for (Color newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x - 1, y};
                }
            }
        }
        return new int[]{x , y};
    }

    /**
     * Calculates the position if the thief moves down
     * @param level the level the thief is on
     * @param x the x position of the thief
     * @param y the y position of the thief
     * @return the new position of the thief
     */
    @Override
    public int[] moveDown(Level level, int x, int y) {
        if (y + 1 < level.MAX_HEIGHT) {
            Color[] colorsOnNewTile = level.getTileColor(x, y + 1);
            for (Color newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x, y + 1};
                }
            }
        }
        return new int[]{x , y};
    }

    /**
     * Calculates the position if the thief moves right
     * @param level the level the thief is on
     * @param x the x position of the thief
     * @param y the y position of the thief
     * @return the new position of the thief
     */
    @Override
    public int[] moveUp(Level level, int x, int y) {
        if (y - 1 >= 0) {
            Color[] colorsOnNewTile = level.getTileColor(x, y - 1);
            for (Color newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x, y - 1};
                }
            }
        }
        return new int[]{x , y};
    }

    /**
     * Calculates the position if the thief moves left
     * @param level the level the thief is on
     * @param x the x position of the thief
     * @param y the y position of the thief
     * @return the new position of the thief
     */
    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (x + 1 < level.MAX_WIDTH) {
            Color[] colorsOnNewTile = level.getTileColor(x + 1, y);
            for (Color newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x + 1, y};
                }
            }
        }
        return new int[]{x , y};
    }
}
