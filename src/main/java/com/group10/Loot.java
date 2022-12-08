package com.group10;

public class Loot extends PickUp {
    int value;
    int xpos;
    int ypos;
    public Loot(int x, int y, int value) {
        this.xpos = x;
        this.ypos = y;
        this.value = value;
    }
    
    @Override
    public void onInteract(Player player) {
        int score = player.getScore();
        player.setScore(score + value);
    }
}
