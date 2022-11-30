package com.group10;

import java.util.ArrayList;

public class Gate {
    private boolean isOpen;
    private String colour;

    // When gates are created they need to be added to this array //
    private ArrayList<Gate> gates = new ArrayList<>();

    public Gate(String gateColour) {

        isOpen = false;
        colour = gateColour;
    }

    public boolean getIsOpen() {

        return isOpen;
    }

    public void setIsOpen(String keyColour) {

        // Search through array of gates //

        for (int i = 0; i < gates.size(); i++) {

            // If key colour = gate colour then gate is opened //
            if (keyColour == gates.get(i).colour) {
                gates.get(i).isOpen = true;
            }
        }
    }

}
