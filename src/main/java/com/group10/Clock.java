package com.group10;

public class Clock extends Interaction{

    public void onInteract(Entity entity, Level lvl){
        if (entity instanceof Player) {
            lvl.setTime(lvl.getTime() + 20);
        }
        // If the enemy collects it take time away
        else {
            lvl.setTime(lvl.getTime() - 10);
        }
    }
}
