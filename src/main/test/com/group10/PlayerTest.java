package com.group10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    
    @Test
    void score() {
        Player player = new Player();
        assertEquals(0, player.getScore());
        player.setScore(1);
        assertEquals(1, player.getScore());
    
    
    }
}