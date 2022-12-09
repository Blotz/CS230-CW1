package com.group10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardTest {
    
    @Test
    void newScore() {
        new Leaderboard();
    }
    
    @Test
    void getNumberOfScores() {
        Leaderboard leaderboard = new Leaderboard();
        assertEquals(0, leaderboard.getNumberOfScores());
        leaderboard.addScore("100 Foo Bar");
        assertEquals(1, leaderboard.getNumberOfScores());
        leaderboard.addScore("200 Bar Baz");
        assertEquals(2, leaderboard.getNumberOfScores());
        leaderboard.addScore("300 Baz Qux");
        assertEquals(3, leaderboard.getNumberOfScores());
    
    }
    
    @Test
    void topScore() {
        Leaderboard leaderboard = new Leaderboard();
        assertEquals(null, leaderboard.topScore());
        leaderboard.addScore("100 Foo Bar");
        assertEquals("100 Foo Bar", leaderboard.topScore());
        leaderboard.addScore("200 Bar Baz");
        assertEquals("100 Foo Bar", leaderboard.topScore());
        leaderboard.addScore("300 Baz Qux");
        assertEquals("100 Foo Bar", leaderboard.topScore());
    }
    
    @Test
    void getTopNScore() {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addScore("100 Foo Bar");
        leaderboard.addScore("200 Bar Baz");
        leaderboard.addScore("300 Baz Qux");
    
        String[] scores;
        scores = leaderboard.getTopNScore(0);
        scores = leaderboard.getTopNScore(3);
        try {
            scores = leaderboard.getTopNScore(-1);
            fail();
        } catch (IllegalArgumentException e) {
        
        }
        try {
            scores = leaderboard.getTopNScore(4);
            fail();
        } catch (IllegalArgumentException e) {
        
        }
        scores = leaderboard.getTopNScore(3);
        assertEquals("100 Foo Bar", scores[0]);
        assertEquals("200 Bar Baz", scores[1]);
        assertEquals("300 Baz Qux", scores[2]);
        
    }

}