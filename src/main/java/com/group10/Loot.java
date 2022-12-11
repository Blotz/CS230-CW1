package com.group10;

/**
 * A piece of loot which can be picked up in the game
 */
public class Loot extends PickUp{

    private String gemName;
    int value;
    
    private static final String FORMAT = "%s";
    
    /**
     * Sets the value of the gem and the name of the gem
     * @param gem the name of the gem
     */
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
    public String toString() {
        return String.format(FORMAT, gemName);
    }
    
    /**
     * Adds the value of the gem to the player's score
     * @param entity the entity that is interacting with the gem
     * @param level the level that the gem is on
     */
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
