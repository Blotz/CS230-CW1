package com.group10;

import java.util.Objects;

/*
 * The exit for the level
 * When an Entity moves into a Tile containing an Exit and has completed the requirements, the level is completed
 */
public class Exit implements Entity{
    private static final String FORMAT = "Exit";
    
    @Override
    public String toString() {
        return String.format(FORMAT);
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
