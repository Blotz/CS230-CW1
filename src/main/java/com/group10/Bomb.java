package com.group10;

public class Bomb extends Interaction {
    private int timer = 3;
    private boolean isExploded = false;
    private int tempX = 0;
    private int tempY = 0;

    public void explosion(Level level, int bombX, int bombY) {
        for (int i = tempX; i < level.MAX_HEIGHT; i++) {
            if (level.getEntity(i, bombY) != null) {
                Entity e = (level.getEntity(i, bombX));

                //Change to Door not switch just place holder.
                boolean val = e instanceof Switch;
                boolean val2 = e instanceof Gate;
                if (val != true && val2 != true) {
                    level.setEntity(null, tempX, bombY);
                }
            }
        }
        for (int p = tempY; p < level.MAX_WIDTH; p++) {
            if (level.getEntity(p, bombX) != null) {
                Entity c = (level.getEntity(p, bombY));

                //Change to Door not switch just place holder.
                boolean val3 = c instanceof Switch;
                boolean val4 = c instanceof Gate;
                if (val3 != true && val4 != true) {
                    level.setEntity(null, tempX, bombY);

                }
            }
        }
    }

                public void bombStart(Entity entity){
                    for (int i = timer; i > 0; i--) {
                        if (i == 0) {
                            isExploded = true;
                        }
                        else if (entity.equals(null)){
                            isExploded = true;
                        }

                    }
                }
            }


