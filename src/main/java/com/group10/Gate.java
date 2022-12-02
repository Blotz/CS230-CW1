package com.group10;

import java.util.ArrayList;

public class Gate implements Entity {
    private boolean isOpen;
    private char[] colour;

    // When gates are created they need to be added to this array //

    public Gate(char[] gateColour) {

        isOpen = false;
        colour = gateColour;
    }

    public boolean getIsOpen() {

        return isOpen;
    }

    public boolean setIsOpen() {
        return isOpen;
    }

    public char[] getColour() {
        return colour;
    }
}
