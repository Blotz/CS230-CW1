package com.group10;

import java.util.ArrayList;
import java.util.Objects;

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

    public void onInteract(Level level) {
        System.out.println("Test");
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
