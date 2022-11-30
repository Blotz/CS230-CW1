package com.group10;

import java.util.Objects;

public class FlyingAssassin extends MoveableEntity{
    private int x;
    private int y;

    public FlyingAssassin(int x, int y) {
        this.x = x;
        this.y = y;
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
        //NEED TO DO
        return true;
    }
}

