package com.group10;

import java.util.Objects;

public class Player extends MoveableEntity {
    private int score = 0;
    private int x;
    private int y;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score && x == player.x && y == player.y;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(score, x, y);
    }

    public int getX() {return this.x;}

    public int getY() {return this.y;}
}