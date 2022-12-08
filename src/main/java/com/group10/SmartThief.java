package com.group10;

import java.util.Random;

/*
 TODO: If the Smart Thief goes over loot it should be picked up.
       Once all the loot is picked up the Smart Thief should go to
       the exit.
       Smart Thief should not go through players, bombs or other NPCs.
 */
public class SmartThief extends MoveableEntity {
    private int x;
    private int y;
    private int MAX_DEPTH;
    private Direction direction;

    public SmartThief(int x, int y, int MAX_DEPTH, Direction direction) {
        this.x = x;
        this.y = y;
        this.MAX_DEPTH = MAX_DEPTH;
        this.direction = direction;
    }
    public int[] move(Level level) {
        int depth = findClosestLoot(level, x, y, 0);
        if (depth >= MAX_DEPTH) {
            return randomMove(level);
        } else {
            switch (direction) {
                case UP:
                    return moveUp(level, x ,y);
                case DOWN:
                    return moveDown(level, x, y);
                case LEFT:
                    return moveLeft(level, x, y);
                case RIGHT:
                    return moveRight(level, x, y);
            }
        }
        return new int[]{x, y};
    }

    private int[] randomMove(Level level) {
        Random rand = new Random();
        int randNum = rand.nextInt(3);
        if (randNum == 0) {
            return moveUp(level, x, y);
        } else if (randNum == 1) {
            return moveDown(level, x, y);
        } else if (randNum == 2) {
            return moveLeft(level, x, y);
        } else {
            return moveRight(level, x ,y);
        }
    }

    private int findClosestLoot(Level level, int x, int y, int depth) {
        //Base case
        if (depth >= MAX_DEPTH) {
            return MAX_DEPTH;
        }
        int curDepth = MAX_DEPTH;

        //Check left movement
        int[] leftMove = moveLeft(level, x, y);
        if (leftMove[0] != x || leftMove[1] != y) {
            //Left base case
            if (leftMove[0] == Level.isLoot && leftMove[1].isLoot) {
                direction = Direction.LEFT;
                return depth;
            } else {
                int newDepth = findClosestLoot(level, leftMove[0], leftMove[1], depth + 1);
                if (newDepth < curDepth) {
                    curDepth = newDepth;
                    direction = Direction.LEFT;
                }
            }
        }
        //Check right movement
        int[] rightMove = moveRight(level, x, y);
        if (rightMove[0] != x || rightMove[1] != y) {
            //Right base case
            if (rightMove[0] == Level.isLoot && rightMove[1].isLoot) {
                direction = Direction.RIGHT;
                return depth;
            } else {
                int newDepth = findClosestLoot(level, rightMove[0], rightMove[1], depth + 1);
                if (newDepth < curDepth) {
                    curDepth = newDepth;
                    direction = Direction.RIGHT;
                    return depth;
                }
            }
        }
        //Check up movement
        int[] upMove = moveUp(level, x, y);
        if (upMove[0] != x || upMove[1] != y) {
            //Up base case
            if (upMove[0] == Level.isLoot && upMove[1].isLoot) {
                direction = Direction.UP;
                return depth;
            } else {
                int newDepth = findClosestLoot(level, upMove[0], upMove[1], depth + 1);
                if (newDepth < curDepth) {
                    curDepth = newDepth;
                    direction = Direction.UP;
                    return depth;
                }
            }
        }
        //Check down movement
        int[] downMove = moveDown(level, x, y);
        if (downMove[0] != x || downMove[1] != y) {
            //Down base case
            if (downMove[0] == Level.isLoot && downMove[1].isLoot) {
                direction = Direction.DOWN;
                return depth;
            } else {
                int newDepth = findClosestLoot(level, downMove[0], downMove[1], depth + 1);
                if (newDepth < curDepth) {
                    curDepth = newDepth;
                    direction = Direction.DOWN;
                    return depth;
                }
            }
        }
        return MAX_DEPTH;
    }
}
