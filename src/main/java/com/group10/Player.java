package com.group10;

import java.util.Objects;

public class Player extends MoveableEntity {
    private int score = 0;
    
    private static final String FORMAT = "Player %d";
    public Player(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT, score);
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int getX(Level level) {
        int[] pos = level.getEntityPosition(this);
        return pos[0];
    }

    public int getY(Level level) {
        int[] pos = level.getEntityPosition(this);
        return pos[1];
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}