package com.group10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MOTDRetrieverTest {
    
    @Test
    void getMOTD() {
        System.out.println(MOTDRetriever.getMOTD());
    }
}