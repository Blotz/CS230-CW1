package com.group10;

public class NPC extends MoveableEntity {
    private Float entitySpeed;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NPC npc = (NPC) o;
        return entitySpeed == npc.entitySpeed;
    }
}
