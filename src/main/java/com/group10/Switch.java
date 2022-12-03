package com.group10;

import java.util.ArrayList;

public class Switch extends PickUp {

    private ArrayList<Gate> gates = new ArrayList<>();
    private char[] colour;

    public Switch (char[] col){
        this.colour = col;
    }


}
