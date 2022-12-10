package com.group10;

public class Tile {
    private Color[] tiles = new Color[4] ;
    public Tile (Color c1, Color c2, Color c3, Color c4) {
        tiles[0] = c1;
        tiles[1] = c2;
        tiles[2] = c3;
        tiles[3] = c4;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (Color c : tiles) {
            s += Level.colorToChar(c);
        }
        return s;
    }
    
    public Color[] getColors() {
        return tiles;
    }
}
