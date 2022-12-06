package com.group10;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    
    @Test
    void mainMenu() {
        Stage stage = new Stage();
        new Menu(stage).mainMenu();
        
        System.out.println("Code complies and returns parent");
    }
}