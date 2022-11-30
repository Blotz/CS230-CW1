package com.group10;

public class FloorFollowingThief extends MoveableEntity {
    private int x;
    private int y;
    private char colour;

    public FloorFollowingThief(int x, int y, char colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
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

}
