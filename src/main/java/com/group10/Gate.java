package com.group10;

/**
 * A Gate which can be opened by a switch
 */
public class Gate implements Entity {
    private Color colour;
    private static final String FORMAT = "Gate %s";

    public Gate(Color gateColour) {
        colour = gateColour;
    }
    
    @Override
    public String toString() {
        String gateColour = "";
        return String.format(FORMAT, Level.colorToChar(colour));
    }
    
    /**
     * Opens the gate
     * @param level the level that the gate is on
     */
    public void setOpen(Level level) {
        int[] pos = level.getEntityPosition(this);
        level.setEntity(null, pos[0], pos[1]);
    }

    public Color getColour() {
        return colour;
    }
}
