package com.group10;

public class Profile {
    private String playerName;
    private int maxLevel;

    public Profile(String playerName, int maxLevel) {
        this.playerName = playerName;
        this.maxLevel = maxLevel;
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
