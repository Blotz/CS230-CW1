package com.group10;


public abstract class Loot extends PickUp{

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
    public void onInteract(Player p, Level lvl, int currX, int currY) {
        Loot loot = (Loot) lvl.getEntity(currX, currY);
    
        p.setScore(p.getScore() + value);
    }

    public String getGemName() {
        return gemName;
    }
}
