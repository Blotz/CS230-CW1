package com.group10;

import java.util.ArrayList;

public class Gate implements Entity {
    private boolean isOpen;
    private Color[] colour;
    private static final String FORMAT = "Gate %s";
    public Gate(Color[] gateColour) {
        isOpen = false;
        colour = gateColour;
    }
    
    @Override
    public String toString() {
        String gateColour = "";
        for (Color c : colour) {
            gateColour += Level.colorToChar(c);
        }
        return String.format(FORMAT, gateColour);
    }
    
    public boolean getIsOpen() {
        return isOpen;
    }

    public boolean setIsOpen() {
        return isOpen = true;
    }

    public Color[] getColour() {
        return colour;
    }
}
