package com.group10;

import java.util.Objects;

/*
TODO: Get FFT to pick up loot as it goes over it.
 */
public class FloorFollowingThief extends MoveableEntity {
    private int x;
    private int y;
    private Direction initDirection;
    private Direction direction;
    private char colour;

    public FloorFollowingThief(int x, int y, Direction initDirection, char colour) {
        this.x = x;
        this.y = y;
        this.initDirection = initDirection;
        this.direction = initDirection;
        this.colour = colour;
    }

    public int[] move(Level level) {
        int[] newPos = {x, y};
        int[] upPos = moveUp(level,x , y);
        int[] downPos = moveDown(level, x, y);
        int[] leftPos = moveLeft(level, x, y);
        int[] rightPos = moveRight(level, x, y);
        //Need to do if FFT is initilised in diffrent directions
        return moveClockwise(level, newPos, upPos, downPos, leftPos, rightPos);
    }

    public int[] moveClockwise(Level level, int[] newPos, int[] upPos, int[] downPos, int[] leftPos, int[] rightPos) {
        switch (direction) {
            case RIGHT:
                if (upPos != newPos) { //Most important to keep left hand rule.
                    newPos = upPos;
                    direction = Direction.UP;
                } else if (rightPos != newPos) { //Next most important to move straight
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else if (downPos != newPos) { //Next most important to move round corners or obstacles
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else { //Final move if a reverse is needed
                    newPos = leftPos;
                    direction = Direction.LEFT;
                }
                return newPos;
            case LEFT:
                if (downPos != newPos) {
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else if (upPos != newPos) {
                    newPos = upPos;
                    direction = Direction.UP;
                } else {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                }
                return newPos;
            case UP:
                if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else if (upPos != newPos) {
                    newPos = upPos;
                    direction = Direction.UP;
                } else if (rightPos != newPos) {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else {
                    newPos = downPos;
                    direction = Direction.DOWN;
                }
                return newPos;
            case DOWN:
                if (rightPos != newPos) {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else if (downPos != newPos) {
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else {
                    newPos = upPos;
                    direction = Direction.UP;
                }
                return newPos;
        }
        return new int[]{x, y}; //Return no move if it has all gone tits up
    }

    public int[] moveAntiClockwise(Level level, int[] newPos, int[] upPos, int[] downPos, int[] leftPos, int[] rightPos) {
        //PLACEHOLDER CODE...
        switch (direction) {
            case RIGHT:
                if (upPos != newPos) { //Most important to keep left hand rule.
                    newPos = upPos;
                    direction = Direction.UP;
                } else if (rightPos != newPos) { //Next most important to move straight
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else if (downPos != newPos) { //Next most important to move round corners or obstacles
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else { //Final move if a reverse is needed
                    newPos = leftPos;
                    direction = Direction.LEFT;
                }
                return newPos;
            case LEFT:
                if (downPos != newPos) {
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else if (upPos != newPos) {
                    newPos = upPos;
                    direction = Direction.UP;
                } else {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                }
                return newPos;
            case UP:
                if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else if (upPos != newPos) {
                    newPos = upPos;
                    direction = Direction.UP;
                } else if (rightPos != newPos) {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else {
                    newPos = downPos;
                    direction = Direction.DOWN;
                }
                return newPos;
            case DOWN:
                if (rightPos != newPos) {
                    newPos = rightPos;
                    direction = Direction.RIGHT;
                } else if (downPos != newPos) {
                    newPos = downPos;
                    direction = Direction.DOWN;
                } else if (leftPos != newPos) {
                    newPos = leftPos;
                    direction = Direction.LEFT;
                } else {
                    newPos = upPos;
                    direction = Direction.UP;
                }
                return newPos;
        }
        return new int[]{x, y}; //Return no move if it has all gone tits up
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorFollowingThief that = (FloorFollowingThief) o;
        return x == that.x && y == that.y && colour == that.colour && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction, colour);
    }
}
