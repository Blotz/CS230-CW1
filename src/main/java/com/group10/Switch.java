package com.group10;

/**
 * An item which can be picked up in the game.
 * Causes all gates which share the same color to open.
 */
public class Switch extends PickUp {
    private Color colour;
    private static final String FORMAT = "Switch %s";

    public Switch (Color col) {
        this.colour = col;
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT, Level.colorToChar(colour));
    }
    
    /**
     * Opens all gates of the same colour as the switch
     * @param level the level that the switch is on
     */
    public void onInteract(Level level) {
        for (int x = 0; x < level.MAX_WIDTH; x++) {
            for (int y = 0; y < level.MAX_HEIGHT; y++) {
                Entity tempEntity = level.getEntity(x, y);
                if (tempEntity instanceof Gate ) {
                    if (((Gate) tempEntity).getColour() == colour) {
                        ((Gate) tempEntity).setOpen(level);
                    }
                }
            }
        }
    }

    public Color getColour (){
        return this.colour;
    }
}
