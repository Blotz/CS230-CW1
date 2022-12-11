package com.group10;

public class Clock extends Interaction{

    private static final int ADD_TIME = 20;
    private static final int REMOVE_TIME = 10;
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
