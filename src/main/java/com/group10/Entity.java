package com.group10;

/**
 * The base class which all entities in the game extend.
 * Requires entity to have an equals method.
 */
public interface Entity {
    @Override
    public boolean equals(Object obj);
}
