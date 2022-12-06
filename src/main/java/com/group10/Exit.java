package com.group10;

import java.util.Objects;

public class Exit implements Entity{
    private boolean isOpen;
    
    public Exit() {
        isOpen = false;
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
