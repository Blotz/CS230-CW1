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
        int[] newPos;
        if (direction == Direction.RIGHT) {
            newPos = moveRight(level, x, y);
            //Needs check for if end has been reached
            return newPos;
        } else if (direction == Direction.LEFT) {
            newPos = moveLeft(level, x, y);
            return newPos;
        } else if (direction == Direction.UP) {
            newPos = moveUp(level, x, y);
            return newPos;
        } else if (direction == Direction.DOWN) {
            newPos = moveDown(level, x, y);
            return newPos;
        }
        return new int[]{x, y};
    }

    public boolean leftHandTouching() {
        return true; //Placeholder method to make sure the left hand of FFT is touching a wall with left hand.
    }

    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (x + 1 <= level.MAX_WIDTH) {
            char[] colorsOnNewTile = level.getTileColor(x + 1, y);
            for (char newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x + 1, y};
                }
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveLeft(Level level, int x, int y) {
        if (x - 1 >= 0) {
            char[] colorsOnNewTile = level.getTileColor(x - 1, y);
            for (char newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x - 1, y};
                }
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveDown(Level level, int x, int y) {
        if (y - 1 >= 0) {
            char[] colorsOnNewTile = level.getTileColor(x, y - 1);
            for (char newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x, y - 1};
                }
            }
        }
        return new int[]{x , y};
    }

    @Override
    public int[] moveUp(Level level, int x, int y) {
        if (y + 1 <= level.MAX_HEIGHT) {
            char[] colorsOnNewTile = level.getTileColor(x, y + 1);
            for (char newColor : colorsOnNewTile) {
                if (newColor == colour) {
                    return new int[]{x, y + 1};
                }
            }
        }
        return new int[]{x , y};
    }
}
