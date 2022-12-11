package com.group10;

import java.util.Objects;

/*
TODO: Get FFT to pick up loot as it goes over it.
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
    
    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int[] newPos;
        Entity entity

        // Turn left?
        newPos = switch (direction) {
            case UP -> moveLeft(level, pos[0], pos[1]);
            case DOWN -> moveRight(level, pos[0], pos[1]);
            case LEFT -> moveDown(level, pos[0], pos[1]);
            case RIGHT -> moveUp(level, pos[0], pos[1]);
        };
        
        entity = level.getEntity(newPos[0], newPos[1]);
        if ((newPos[0] != pos[0] || newPos[1] != pos[1])
            && (entity != null
                && (!(entity instanceof Switch) || !(entity instanceof Loot))
          )) {
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
        if (newPos[0] != pos[0] || newPos[1] != pos[1] || entity == null || entity instanceof Switch || entity instanceof Loot) {
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorFollowingThief that = (FloorFollowingThief) o;
        return initDirection == that.initDirection && direction == that.direction && colour == that.colour;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(initDirection, direction, colour);
    }
}
