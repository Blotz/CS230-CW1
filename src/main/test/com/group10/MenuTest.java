package com.group10;

import javafx.scene.Parent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    
    @Test
    void mainMenu() {
        Parent parent = Menu.mainMenu();
        System.out.println("Code complies and returns parent");
    }
}