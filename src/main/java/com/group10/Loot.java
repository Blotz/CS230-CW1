package com.group10;

public class Loot extends PickUp {
    int value;
    int xpos;
    int ypos;
    public Loot(int value) {
        this.value = value;
    }
    
    @Override
    public void onInteract(Player player) {
        int score = player.getScore();
        player.setScore(score + value);
    }
}
