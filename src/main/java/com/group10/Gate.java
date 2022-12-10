package com.group10;

import java.util.ArrayList;

public class Gate implements Entity {
    private boolean isOpen;

    private Color colour;
    private static final String FORMAT = "Gate %s";

    public Gate(Color gateColour) {
        isOpen = false;
        colour = gateColour;
    }
    
    @Override
    public String toString() {
        String gateColour = "";
        return String.format(FORMAT, Level.colorToChar(colour));
    }
    
    public boolean getIsOpen() {
        return isOpen;
    }

    public boolean setOpen(Level level) {
        int[] pos = level.getEntityPosition(this);
        level.setEntity(null, pos[0], pos[1]);
        return isOpen = true;
    }

    public Color getColour() {
        return colour;
    }
}
