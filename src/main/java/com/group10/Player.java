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
    
    /**
     * Moves the player in the down direction
     * @param level the level that the entity is on
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     * @return
     */
    @Override
    public int[] moveDown(Level level, int x, int y) {
        if (y == level.MAX_HEIGHT - 1) {
            Color[] colorsOnCurTile = level.getTileColorEntity(this);
            Color[] colorsOnNewTile = level.getTileColor(x, 0);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{x, 0};
                    }
                }
            }
        }
        return super.moveDown(level, x, y);
    }
    
    /**
     * Moves the player in the up direction
     * @param level the level that the entity is on
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     * @return
     */
    @Override
    public int[] moveUp(Level level, int x, int y) {
        if (y == 0) {
            Color[] colorsOnCurTile = level.getTileColorEntity(this);
            Color[] colorsOnNewTile = level.getTileColor(x, level.MAX_HEIGHT - 1);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{x, level.MAX_HEIGHT - 1};
                    }
                }
            }
        }
        return super.moveUp(level, x, y);
    }
    
    /**
     * Moves the player in the right direction
     * @param level the level that the entity is on
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     * @return
     */
    @Override
    public int[] moveRight(Level level, int x, int y) {
        if (x == level.MAX_WIDTH - 1) {
            Color[] colorsOnCurTile = level.getTileColorEntity(this);
            Color[] colorsOnNewTile = level.getTileColor(0, y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{0, y};
                    }
                }
            }
        }
        return super.moveRight(level, x, y);
    }
    
    /**
     * Moves the player in the left direction
     * @param level the level that the entity is on
     * @param x the x coordinate of the entity
     * @param y the y coordinate of the entity
     * @return
     */
    @Override
    public int[] moveLeft(Level level, int x, int y) {
        if (x == 0) {
            Color[] colorsOnCurTile = level.getTileColorEntity(this);
            Color[] colorsOnNewTile = level.getTileColor(level.MAX_WIDTH - 1, y);
            for (Color newColor: colorsOnNewTile) {
                for (Color curColor: colorsOnCurTile) {
                    if (curColor == newColor) {
                        return new int[]{level.MAX_WIDTH - 1, y};
                    }
                }
            }
        }
        return super.moveLeft(level, x, y);
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