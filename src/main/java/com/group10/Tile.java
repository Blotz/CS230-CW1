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
        return String.valueOf(tiles[0]) + String.valueOf(tiles[1]) + String.valueOf(tiles[2]) + String.valueOf(tiles[3]);
    }
    
    public Color[] getColors() {
        return tiles;
    }
}
