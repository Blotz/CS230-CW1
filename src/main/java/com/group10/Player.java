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

    public void movePlayerRight(Level level) {
        int[] newC = this.moveRight(level, this.x, this.y);
       this.x = newC[0];
    }

    public void movePlayerLeft(Level level) {
        int[] newC = this.moveLeft(level, this.x, this.y);
        this.x = newC[0];
    }

    public void movePlayerUp(Level level) {
        int[] newC = this.moveUp(level, this.x, this.y);
        this.y = newC[1];
    }

    public void movePlayerDown(Level level) {
        int[] newC = this.moveDown(level, this.x, this.y);
        this.y = newC[1];
    }
}