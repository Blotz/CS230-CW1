package com.group10;

import java.util.ArrayList;

public class Switch extends PickUp {

    private ArrayList<Gate> gates = new ArrayList<>();
    private char[] colour;

    public Switch (char[] col) {
        this.colour = col;
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

    private char[] getColour (){
        return this.colour;
    }
}
