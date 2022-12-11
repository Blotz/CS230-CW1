package com.group10;

/**
 * This base class which all interactable objects in the game extend.
 */
public abstract class Interaction extends PickUp {
    private int duration;

    @Override
    public void onInteract(Entity player, Level lvl){
    }

    public void effect(){
    }

}
