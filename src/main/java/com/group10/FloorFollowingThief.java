package com.group10;

public class FloorFollowingThief extends MoveableEntity {
    private int x;
    private int y;
    private Direction direction;
    private char colour;

    public FloorFollowingThief(int x, int y, Direction direction, char colour) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.colour = colour;
    }

    public int[] move(Level level) {
        if (direction == Direction.RIGHT) {
            return moveRight(level, x, y);
        } else if (direction == Direction.LEFT) {
            return moveLeft(level, x ,y);
        } else if (direction == Direction.UP) {
            return moveUp(level, x, y);
        } else if (direction == Direction.DOWN) {
            return moveDown(level, x, y);
        }
        return new int[]{x, y};
    }

    @Override
    public int[] moveRight(Level level, int x, int y) {
        char[] colorsOnNewTile = level.getTileColor(x + 1, y);
        for (char newColor : colorsOnNewTile) {
            if (newColor == colour) {
                return new int[]{x + 1, y};
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveLeft(Level level, int x, int y) {
        char[] colorsOnNewTile = level.getTileColor(x - 1, y);
        for (char newColor : colorsOnNewTile) {
            if (newColor == colour) {
                return new int[]{x - 1, y};
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveDown(Level level, int x, int y) {
        char[] colorsOnNewTile = level.getTileColor(x, y - 1);
        for (char newColor : colorsOnNewTile) {
            if (newColor == colour) {
                return new int[]{x, y - 1};
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveUp(Level level, int x, int y) {
        char[] colorsOnNewTile = level.getTileColor(x, y + 1);
        for (char newColor : colorsOnNewTile) {
            if (newColor == colour) {
                return new int[]{x, y + 1};
            }
        }
        return new int[]{x , y};
    }
}
