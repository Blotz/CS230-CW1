package com.group10;

public abstract class Loot extends PickUp{

    private String gemName;

    public Loot(String gem){
        gemName = gem;
    }

    public void lootValue(Player player, Loot loot) {
            if (loot.getGemName() == "Cent") {
                player.setScore(player.getScore() + 10);
            } else if (loot.getGemName() == "Dollar") {
                player.setScore(player.getScore() + 10);
            } else if (loot.getGemName() == "Ruby") {
                player.setScore(player.getScore() + 10);
            } else if (loot.getGemName() == "Diamond") {
                player.setScore(player.getScore() + 10);
            }
    }

    public String getGemName() {
        return gemName;
    }
}