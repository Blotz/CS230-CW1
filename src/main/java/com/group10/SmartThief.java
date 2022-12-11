package com.group10;

import java.util.Objects;
import java.util.Random;


public class SmartThief extends MoveableEntity {
    private static final int MAX_DEPTH = 10;
    private Direction direction = Direction.UP;

    private static final String FORMAT = "SmartThief";
    
    public SmartThief() {
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT);
    }

    public int[] move(Level level) {
        int[] pos = level.getEntityPosition(this);
        int x = pos[0];
        int y = pos[1];

        // Is there loot on the map?
        if (level.isLootOnMap() || level.isSwitchOnMap()) {
            System.out.println("Loot on map");
            int depth = findClosestLoot(level, x, y, 0);
            System.out.println("Depth: " + depth);
            System.out.println("Direction: " + direction);
            return moveInDirection(level, x, y);
        }
        // Move towards exit
        System.out.println("No loot on map");
        int depth = findClosestExit(level, x, y, 0);
        System.out.println("Depth: " + depth);
        System.out.println("Direction: " + direction);
        return moveInDirection(level, x, y);

    }

    private int[] moveInDirection(Level level, int x, int y) {
        switch (direction) {
            case UP:
                return moveUp(level, x, y);
            case DOWN:
                return moveDown(level, x, y);
            case LEFT:
                return moveLeft(level, x, y);
            case RIGHT:
                return moveRight(level, x, y);
        }
        return new int[]{x, y};
    }

    private int randomMove(int depth) {
        Random rand = new Random();
        int randNum = rand.nextInt(4);
        if (randNum == 0) {
            this.direction = Direction.UP;
            return depth;
        } else if (randNum == 1) {
            this.direction = Direction.DOWN;
            return depth;
        } else if (randNum == 2) {
            this.direction = Direction.LEFT;
            return depth;
        } else {
            this.direction = Direction.RIGHT;
            return depth;
        }
    }

    private int findClosestLoot(Level level, int x, int y, int depth) {
        //Base case
        if (depth >= MAX_DEPTH) {
            return MAX_DEPTH;
        }
        int leftDepth = MAX_DEPTH;
        int rightDepth = MAX_DEPTH;
        int upDepth = MAX_DEPTH;
        int downDepth = MAX_DEPTH;
        Entity entity;

        int[] left = moveLeft(level, x, y);
        entity = level.getEntity(left[0], left[1]);
        if (entity instanceof Loot || entity instanceof Switch) {
            direction = Direction.LEFT;
            return depth;
        } else if (level.getEntity(left[0], left[1]) == null) {
            leftDepth = findClosestLoot(level, left[0], left[1], depth + 1);
        }
        int[] right = moveRight(level, x, y);
        entity = level.getEntity(right[0], right[1]);
        if (entity instanceof Loot || entity instanceof Switch) {
            direction = Direction.RIGHT;
            return depth;
        } else if (level.getEntity(right[0], right[1]) == null) {
            rightDepth = findClosestLoot(level, right[0], right[1], depth + 1);
        }
        int[] up = moveUp(level, x, y);
        entity = level.getEntity(up[0], up[1]);
        if (entity instanceof Loot || entity instanceof Switch) {
            direction = Direction.UP;
            return depth;
        } else if (level.getEntity(up[0], up[1]) == null) {
            upDepth = findClosestLoot(level, up[0], up[1], depth + 1);
        }
        int[] down = moveDown(level, x, y);
        entity = level.getEntity(down[0], down[1]);
        if (entity instanceof Loot || entity instanceof Switch) {
            direction = Direction.DOWN;
            return depth;
        } else if (level.getEntity(down[0], down[1]) == null) {
            downDepth = findClosestLoot(level, down[0], down[1], depth + 1);
        }

        // set direction to closest loot
        return findClosest(leftDepth, rightDepth, upDepth, downDepth);
    }

    private int findClosestExit(Level level, int x, int y, int depth) {
        //Base case
        if (depth >= MAX_DEPTH) {
            return MAX_DEPTH;
        }
        int leftDepth = MAX_DEPTH;
        int rightDepth = MAX_DEPTH;
        int upDepth = MAX_DEPTH;
        int downDepth = MAX_DEPTH;


        int[] left = moveLeft(level, x, y);
        if (level.getEntity(left[0], left[1]) instanceof Exit) {
            direction = Direction.LEFT;
            return depth;
        } else if (level.getEntity(left[0], left[1]) == null) {
            leftDepth = findClosestExit(level, left[0], left[1], depth + 1);
        }
        int[] right = moveRight(level, x, y);
        if (level.getEntity(right[0], right[1]) instanceof Exit) {
            direction = Direction.RIGHT;
            return depth;
        } else if (level.getEntity(right[0], right[1]) == null) {
            rightDepth = findClosestExit(level, right[0], right[1], depth + 1);
        }
        int[] up = moveUp(level, x, y);
        if (level.getEntity(up[0], up[1]) instanceof Exit) {
            direction = Direction.UP;
            return depth;
        } else if (level.getEntity(up[0], up[1]) == null) {
            upDepth = findClosestExit(level, up[0], up[1], depth + 1);
        }
        int[] down = moveDown(level, x, y);
        if (level.getEntity(down[0], down[1]) instanceof Exit) {
            direction = Direction.DOWN;
            return depth;
        } else if (level.getEntity(down[0], down[1]) == null) {
            downDepth = findClosestExit(level, down[0], down[1], depth + 1);
        }

        // set direction to closest loot
        return findClosest(leftDepth, rightDepth, upDepth, downDepth);
    }

    private int findClosest(int leftDepth, int rightDepth, int upDepth, int downDepth) {
//        System.out.println("Left: " + leftDepth + " Right: " + rightDepth + " Up: " + upDepth + " Down: " + downDepth);
        if (leftDepth < rightDepth && leftDepth < upDepth && leftDepth < downDepth) {
            direction = Direction.LEFT;
            return leftDepth;
        }
        if (rightDepth < leftDepth && rightDepth < upDepth && rightDepth < downDepth) {
            direction = Direction.RIGHT;
            return rightDepth;
        }
        if (upDepth < leftDepth && upDepth < rightDepth && upDepth < downDepth) {
            direction = Direction.UP;
            return upDepth;
        }
        if (downDepth < leftDepth && downDepth < rightDepth && downDepth < upDepth) {
            direction = Direction.DOWN;
            return downDepth;
        }
        return randomMove(leftDepth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartThief that = (SmartThief) o;
        return direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}
