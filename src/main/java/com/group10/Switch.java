package com.group10;

import java.util.ArrayList;

public class Switch extends PickUp {

    private ArrayList<Gate> gates = new ArrayList<>();
    private Color[] colour;
    private static final String FORMAT = "Switch %s";

    public Switch (Color[] col) {
        this.colour = col;
    }
    
    @Override
    public String toString() {
        String switchColour = "";
        for (Color c : colour) {
            switchColour += Level.colorToChar(c);
        }
        return String.format(FORMAT, switchColour);
    }

    public void switchInteract (Switch sw){
        for (int i = 0; i < gates.size(); i++){
            if (sw.getColour() == gates.get(i).getColour()){
                if (gates.get(i).getIsOpen() == false){
                    gates.get(i).setIsOpen();
                }
            }
        }
    }

    public void onInteract(Entity entity, Level level) {
        switchInteract(this);
    }

    private Color[] getColour (){
        return this.colour;
    }
}
