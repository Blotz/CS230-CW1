package com.group10;

import java.util.ArrayList;

public class Bomb extends Interaction {
    private int COUNTDOWN = 10;
    private boolean timerStarts = false;

    public void update(Level level) {
        // Check the near by squares
        if (!timerStarts) {
            cheakNearby(level);
        }
        if (timerStarts) {
            countdown(level);
        }

    }

    private void cheakNearby(Level level) {
        // Check the near by squares
        int[] pos = level.getEntityPosition(this);

        boolean hasEntity = check(level, pos[0], pos[1] + 1);  // up
        hasEntity = hasEntity || check(level, pos[0], pos[1] - 1);  // down
        hasEntity = hasEntity || check(level, pos[0] - 1, pos[1]);  // left
        hasEntity = hasEntity || check(level, pos[0] + 1, pos[1]);  // right

        if (hasEntity) {
            timerStarts = true;
        }
    }

    private boolean check(Level level, int x, int y) {

        Entity entity = level.getEntity(x, y);

        if (entity instanceof Player) {
            return true;
        } else if (entity instanceof FlyingAssassin) {
            return true;
        } else if (entity instanceof FlyingAssassin) {
            return true;
        } else {
            return false;
        }
    }

    private void countdown(Level level) {
        COUNTDOWN--;
        if (COUNTDOWN == 0) {
            System.out.println("boom town");
            explosion(level);
        }
    }

    public void explosion(Level level) {

        // Collects the y : [0] and x : [1] for the bomb position
        int[] bombPos = level.getEntityPosition(this);

        for (int x = 0; x < level.MAX_WIDTH; x++) {
            Entity entity = level.getEntity(x, bombPos[1]);
            if (entity != null && (!(entity instanceof Gate) || !(entity instanceof Exit))) {

                level.setEntity(null, x, bombPos[1]);

            }
        }
        for (int y = 0; y < level.MAX_HEIGHT; y++) {
            Entity entity = level.getEntity(bombPos[0], y);
            if (entity != null && (!(entity instanceof Gate) || !(entity instanceof Exit))) {

                level.setEntity(null, bombPos[0], y);
            }
        }
    }
}
