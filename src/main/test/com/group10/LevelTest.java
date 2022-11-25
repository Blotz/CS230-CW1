package com.group10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

  @Test
  void loadLevelFile() {
    Level level = new Level("level/level1.txt");
  }
}