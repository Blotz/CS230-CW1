package com.group10;

import java.util.Objects;


/**
 * The Flying Assassin NPC is an enemy that can fly and kill the player.
 */
public class FlyingAssassin extends MoveableEntity{
    private Direction direction;
    
    private static final String FORMAT = "FlyingAssassin %s";
                                          // changed to make it work for now, cannot create direction from level
    public FlyingAssassin(Direction direction) {  //public FlyingAssassin(int x, int y, Direction direction) {
        this.direction = direction;
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT, Level.directionToString(direction));
    }
    
    /**
     * Calculates the next direction the assassin should move in
     * @return next position the thief should move to
     */
    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int[] newPos;
        Entity entity;

        switch (direction) {
            case UP:
                 newPos = moveUp(level, pos[0], pos[1]);
                 entity = level.getEntity(newPos[0], newPos[1]);
                 if (newPos[0] == pos[0] && newPos[1] == pos[1] ||
                         (entity != null && !(entity instanceof Player || entity instanceof FlyingAssassin))) {
                     direction = Direction.DOWN;
                     return move(level);
                 } else {
                     return newPos;
                 }
            case DOWN:
                newPos = moveDown(level, pos[0], pos[1]);
                entity = level.getEntity(newPos[0], newPos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1] ||
                        (entity != null && !(entity instanceof Player || entity instanceof FlyingAssassin))) {
                    direction = Direction.UP;
                    return move(level);
                } else {
                    return newPos;
                }
            case LEFT:
                newPos = moveLeft(level, pos[0], pos[1]);
                entity = level.getEntity(newPos[0], newPos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1] ||
                        (entity != null && !(entity instanceof Player || entity instanceof FlyingAssassin))) {
                    direction = Direction.RIGHT;
                    return move(level);
                } else {
                    return newPos;
                }
            case RIGHT:
                newPos = moveRight(level, pos[0], pos[1]);
                entity = level.getEntity(newPos[0], newPos[1]);
                if (newPos[0] == pos[0] && newPos[1] == pos[1] ||
                        (entity != null && !(entity instanceof Player || entity instanceof FlyingAssassin))) {
                    direction = Direction.LEFT;
                    return move(level);
                } else {
                    return newPos;
                }
        }
        return pos;
    }

    /**
     * Moves the assassin right
     * @level the level the assassin is on
     * @x the x position of the assassin
     * @y the y position of the assassin
     * @return next position the thief should move to
     */
    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (level.MAX_WIDTH <= x + 1) {
            return new int[]{x, y};
        }
        return new int[]{x + 1, y};
    }

    /**
     * Moves the assassin left
     * @level the level the assassin is on
     * @x the x position of the assassin
     * @y the y position of the assassin
     * @return next position the thief should move to
     */
    @Override
    public int[] moveLeft(Level level, int x, int y) {
        if (x - 1 < 0) {
            return new int[]{x, y};
        }
        return new int[]{x - 1, y};
    }

    /**
     * Moves the assassin down
     * @level the level the assassin is on
     * @x the x position of the assassin
     * @y the y position of the assassin
     * @return next position the thief should move to
     */
    @Override
    public int[] moveDown(Level level, int x, int y) {
        if (level.MAX_HEIGHT <= y + 1) {
            return new int[]{x, y};
        }
        return new int[]{x, y + 1};
    }
    /**
     * Moves the assassin up
     * @level the level the assassin is on
     * @x the x position of the assassin
     * @y the y position of the assassin
     * @return next position the thief should move to
     */
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

