package com.group10;

public class Switch extends PickUp{
    private String colour;

    private Gate operates;

    public Switch(String colour, Gate gate){
        this.colour = colour;
        this.operates = gate;
    }

    public String getColour(){return this.colour;}


    @Override
    public void onInteract(Player player) {
        if (!operates.getIsOpen()) {
            operates.setIsOpen(colour);  // Based of gate implmentation (Not 100% sure if this is right)
        }
    }

}
