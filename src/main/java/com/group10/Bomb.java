package com.group10;

public class Bomb extends Interaction {
    private int currTimer;
    private boolean timerBegun = false;
    private int tempX = 0;
    private int tempY = 0;

    public void explosion(Level level) {

        // Collects the y : [0] and x : [1] for the bomb position
        int[] bombPos = level.getEntityPosition(this);

        for (int i = tempX; i < level.MAX_HEIGHT; i++) {
            if (level.getEntity(i, bombPos[0]) != null) {
                Entity e = (level.getEntity(i, bombPos[0]));

                //Change to Door not switch just place holder.
                boolean val = e instanceof Switch;
                boolean val2 = e instanceof Gate;
                if (val != true && val2 != true) {
                    level.setEntity(null, tempX, bombPos[0]);
                }
            }
        }
        for (int p = tempY; p < level.MAX_WIDTH; p++) {
            if (level.getEntity(p, bombPos[0]) != null) {
                Entity c = (level.getEntity(p, bombPos[1]));

                //Change to Door not switch just placeholder.
                boolean val3 = c instanceof Switch;
                boolean val4 = c instanceof Gate;
                if (val3 != true && val4 != true) {
                    level.setEntity(null, bombPos[1], tempY);

                }
            }
        }

        // Sets the bombs timer state to false, so it can't keep getting referenced
        timerBegun = false;
    }

    // add bomb to npc array in level. Check if explosion is true. UPDATE function updates every tick
    public void startBomb(int timer) {

        currTimer = timer;
        timerBegun = true;
    }

    public boolean hasTimerBegun() {
        return timerBegun;
    }

    public int getTimer() {
        return currTimer;
    }

    public int countdownTimer(int countdownValue) {
        return currTimer - countdownValue;
    }
}

