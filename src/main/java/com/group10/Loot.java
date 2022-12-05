package com.group10;

public class Loot extends PickUp {
    int value;
    int xpos;
    int ypos;
    
    @Override
    public void onInteract(Player player) {
        int score = player.getScore();
        player.setScore(score + value);
    }
}
