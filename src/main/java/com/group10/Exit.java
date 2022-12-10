package com.group10;

import java.util.Objects;

public class Exit implements Entity{
    private boolean isOpen;
    
    private static final String FORMAT = "Exit";
    
    public Exit() {
        isOpen = false;
    }
    
    @Override
    public String toString() {
        return String.format(FORMAT);
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public void setOpen(boolean open) {
        isOpen = open;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exit exit = (Exit) o;
        return isOpen == exit.isOpen;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(isOpen);
    }
}
