package com.group10;

import java.util.ArrayList;

public class Switch extends PickUp {

    private ArrayList<Gate> gates = new ArrayList<>();
    private Color colour;

    public Switch (Color col) {
        this.colour = col;
    }

    public void addGate(Gate gate) {
        if (gate.getColour() == colour) {
            gates.add(gate);
        } else {
            System.out.println("Switch is not the same color as the gate");
        }
    }

    public void onInteract(Entity entity, Level level) {
        if (entity instanceof Player) {
            for (Gate g : gates) {
                g.setOpen(level);
            }
        }
    }

    public Color getColour (){
        return this.colour;
    }
}
