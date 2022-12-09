package com.group10;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
class LevelTest {
  
  @Test
  void loadFakeLevelFile() {
    try {
      Level level = new Level("test/Level/fakelevel.txt");
      fail();
    } catch (FileNotFoundException e) {
    }
  }
  
  @Test
//  @Disabled
  void loadLevelFile() {
    try {
      Level level = new Level("test/Level/level.txt");
      assertEquals(level.MAX_HEIGHT, 4);
      assertEquals(level.MAX_WIDTH, 5);
      assertEquals(level.getTime(), 180);
    } catch (FileNotFoundException e) {
      fail(e);
    }
  }
}
  