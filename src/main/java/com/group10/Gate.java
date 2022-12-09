package com.group10;

import java.util.ArrayList;

public class Gate implements Entity {
    private boolean isOpen;
    private Color[] colour;

    public Gate(Color[] gateColour) {
        isOpen = false;
        colour = gateColour;
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
