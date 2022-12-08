package com.group10;

import java.util.ArrayList;

public class Gate implements Entity {
    private boolean isOpen;
    private char[] colour;

    public Gate(char[] gateColour) {
        isOpen = false;
        colour = gateColour;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public boolean setIsOpen() {
        return isOpen = true;
    }

    public char[] getColour() {
        return colour;
    }
}
