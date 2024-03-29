package com.group10;

/**
 * This class represents a pickup item which adds time to the player's clock.
 */
public class Clock extends Interaction {
    private static final int ADD_TIME = 20;
    private static final int REMOVE_TIME = 10;
    
    /**
     * Increases the time on the level
     * @param entity the entity that is interacting with the clock
     * @param lvl the level that the clock is on
     */
    @SuppressWarnings("checkstyle:FinalParameters")
    public void onInteract(Entity entity, Level lvl){
        if (entity instanceof Player) {
            lvl.setTime(lvl.getTime() + ADD_TIME);
        }
        
        // If the enemy collects it take time away
        else {
            lvl.setTime(lvl.getTime() - REMOVE_TIME);
        }
    }
    
    @Override
    public String toString() {
        return "Clock";
    }
}
