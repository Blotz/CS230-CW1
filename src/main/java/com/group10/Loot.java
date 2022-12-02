package com.group10;

public abstract class Loot{

    private String gemName;

    public Loot(String gem){
        gemName = gem;
    }

    public static void onInteract(Loot loot) {
        if (loot.getGemName() == "Ruby") {
            // get player score and add x amount
            // And remove object
        }
    }

    public String getGemName() {
        return gemName;
    }
}
