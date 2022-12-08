package com.group10;

import java.util.Objects;

import static com.group10.MoveableEntity.Direction.RIGHT;

public class FlyingAssassin extends MoveableEntity{
    private int x;
    private int y;
    private Direction direction = RIGHT;
                                          // changed to make it work for now, cannot create direction from level
    public FlyingAssassin(int x, int y) {  //public FlyingAssassin(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
      //  this.direction = direction;
    }

    public int[] move(Level level) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            int[] pos = moveVertical(level);
            this.x = pos[0];
            this.y = pos[1];
            return moveVertical(level);
        } else {
            int[] pos = moveHorizontal(level);
            this.x = pos[0];
            this.y = pos[1];
            return moveHorizontal(level);
        }
    }

    private int[] moveHorizontal(Level level) {
        if (direction == RIGHT) {
            if (x < level.MAX_WIDTH) {
                return moveRight(level, x, y);
            } else {
                direction = Direction.LEFT;
                return moveLeft(level, x, y);
            }
        } else if (direction == Direction.LEFT) {
            if (x > 0) {
                return moveLeft(level, x, y);
            } else {
                direction = RIGHT;
                return moveRight(level, x, y);
            }
        }
        return new int[]{x, y};
    }

    private int[] moveVertical(Level level) {
        if (direction == Direction.UP) {
            if (y < level.MAX_HEIGHT) {
                return moveUp(level, x, y);
            } else {
                direction = Direction.DOWN;
                return moveDown(level, x, y);
            }
        } else if (direction == Direction.DOWN) {
            if (y > 0) {
                return moveDown(level, x, y);
            } else {
                direction = Direction.UP;
                return moveUp(level, x, y);
            }
        }
        return new int[]{x, y};
    }

    @Override
    public int[] moveRight(Level level, int x, int y) {
        return new int[]{x + 1, y};
    }

    @Override
    public int[] moveLeft(Level level, int x, int y) {
        return new int[]{x - 1, y};
    }

    @Override
    public int[] moveDown(Level level, int x, int y) {
        return new int[]{x, y - 1};
    }

    @Override
    public int[] moveUp(Level level, int x, int y) {
        return new int[]{x, y + 1};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlyingAssassin that = (FlyingAssassin) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

