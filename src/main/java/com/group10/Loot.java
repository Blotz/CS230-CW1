package com.group10;


public class Loot extends PickUp{

    private String gemName;
    int value;
    

    public Loot(String gem){
        gemName = gem;
        switch (gem) {
            case "Cent":
                value = 5;
                break;
            case "Dollar":
                value = 10;
                break;
            case "Ruby":
                value = 15;
                break;
            case "Diamond":
                value = 20;
                break;
            default:
                throw new IllegalArgumentException("Invalid gem name");
        }
        
    }

    @Override
    public void onInteract(Entity entity, Level level) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.setScore(player.getScore() + value);
        }
        int[] pos = level.getEntityPosition(this);
        level.setEntity(null, pos[0], pos[1]);
    }

    public String getGemName() {
        return gemName;
    }
}
