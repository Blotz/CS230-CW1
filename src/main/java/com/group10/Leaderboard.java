package com.group10;


import java.util.Iterator;
import java.util.PriorityQueue;

public class Leaderboard {
    PriorityQueue<String> highScores;
    public Leaderboard() {
        highScores = new PriorityQueue<String>();
    }
    
    public void newScore(String score) {
        highScores.add(score);
    }
    
    public String topScore() {
        return highScores.peek();
    }
    
    public String[] getTopNScore(int n) {
        if (n < 0 || n > this.getNumberOfScores()) {
            throw new IllegalArgumentException("Number of scores must be greater than 0 and less than or equal to"
              + this.getNumberOfScores());
        }
        String[] scores = new String[n];
        Iterator<String> highScoresIter = highScores.iterator();
        
        for (int i = 0; i < n; i++) {
            if (!highScoresIter.hasNext()) {
                return scores;
            }
            scores[i] = highScoresIter.next();
        }
        return scores;
    }

    public int getNumberOfScores() {
        return highScores.size();
    }
    
    public void addScore(String score) {
        highScores.add(score);
    }
}
