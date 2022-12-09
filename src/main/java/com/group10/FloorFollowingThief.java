package com.group10;

import java.util.Objects;

/*
TODO: Get FFT to pick up loot as it goes over it.
 */
public class FloorFollowingThief extends MoveableEntity {
    private Direction initDirection;
    private Direction direction;
    private final Color colour;

    public FloorFollowingThief(Direction initDirection, Color colour) {
        this.initDirection = initDirection;
        this.direction = initDirection;
        this.colour = colour;
    }

    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int[] upPos = moveUp(level, pos[0] , pos[1]);
        int[] downPos = moveDown(level, pos[0], pos[1]);
        int[] leftPos = moveLeft(level, pos[0], pos[1]);
        int[] rightPos = moveRight(level, pos[0], pos[1]);
        //Need to do if FFT is initilised in diffrent directions
        return moveClockwise(level, pos, upPos, downPos, leftPos, rightPos);
    }

    public int[] moveClockwise(Level level, int[] startingPos, int[] upPos, int[] downPos, int[] leftPos, int[] rightPos) {
        int[] newPos = startingPos;
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
        return startingPos; //Return no move if it has all gone tits up
    }

    public int[] moveAntiClockwise(Level level, int[] startingPos, int[] upPos, int[] downPos, int[] leftPos, int[] rightPos) {
        int[] newPos = startingPos;
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
        return startingPos; //Return no move if it has all gone tits up
    }

    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (x + 1 <= level.MAX_WIDTH) {
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
    public int[] moveUp(Level level, int x, int y) {
        if (y + 1 <= level.MAX_HEIGHT) {
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
