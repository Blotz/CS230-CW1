package com.group10;

public class Profile {
    private String playerName;
    private int maxLevel;

    public Profile(String playerName) {
        this.playerName = playerName;
        maxLevel = 1;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getPlayerName() {
        return playerName;
    }
}
