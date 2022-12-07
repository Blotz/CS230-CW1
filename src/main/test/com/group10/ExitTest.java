package com.group10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {
    
    @Test
    void Open() {
        new Exit();
    }
    @Test
    void isOpen() {
        Exit exit = new Exit();
        assertFalse(exit.isOpen());
    }
    
    @Test
    void setOpen() {
        Exit exit = new Exit();
        exit.setOpen(true);
        assertTrue(exit.isOpen());
        exit.setOpen(false);
        assertFalse(exit.isOpen());
    }
}