package com.group10;

import java.util.Objects;


/*TODO: If the Flying Assassin collides with the player, the player should loose
        and the game should end or level reset.
*  */
public class FlyingAssassin extends MoveableEntity{
    private Direction direction;
                                          // changed to make it work for now, cannot create direction from level
    public FlyingAssassin(Direction direction) {  //public FlyingAssassin(int x, int y, Direction direction) {
        this.direction = direction;
    }

    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int[] newPos;

        switch (direction) {
            case UP:
                 newPos = moveUp(level, pos[0], pos[1]);
                 if (newPos[0] == pos[0] && newPos[1] == pos[1]) {
                     direction = Direction.DOWN;
                     return move(level);
                 } else {
                     return newPos;
                 }
            case DOWN:
                newPos = moveDown(level, pos[0], pos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1]) {
                    direction = Direction.UP;
                    return move(level);
                } else {
                    return newPos;
                }
            case LEFT:
                newPos = moveLeft(level, pos[0], pos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1]) {
                    direction = Direction.RIGHT;
                    return move(level);
                } else {
                    return newPos;
                }
            case RIGHT:
                newPos = moveRight(level, pos[0], pos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1]) {
                    direction = Direction.LEFT;
                    return move(level);
                } else {
                    return newPos;
                }
        }
        return pos;
    }

    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (level.MAX_WIDTH <= x + 1) {
            return new int[]{x, y};
        }
        return new int[]{x + 1, y};
    }

    @Override
    public int[] moveLeft(Level level, int x, int y) {
        if (x - 1 < 0) {
            return new int[]{x, y};
        }
        return new int[]{x - 1, y};
    }

    @Override
    public int[] moveDown(Level level, int x, int y) {
        if (level.MAX_HEIGHT <= y + 1) {
            return new int[]{x, y};
        }
        return new int[]{x, y + 1};
    }

    @Override
    public int[] moveUp(Level level, int x, int y) {
        if (y - 1 < 0) {
            return new int[]{x, y};
        }
        return new int[]{x, y - 1};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlyingAssassin that = (FlyingAssassin) o;
        return direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}

