/* 
 * This file is part of Bomberman.
 *
 * Copyright (M) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (M). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.event.impl;

import com.apache.event.Event;
import com.apache.game.entity.Entity;
import com.apache.map.Location;

/**
 * An {@link Event} implementation sent whenever an {@link Entity} changes its
 * {@link Location}. <strong>Please note that the new {@code Location} of the
 * {@code Entity} will not have been set yet when this method is posted, use the
 * values within this class instead of {@code getLocation} from
 * {@code Entity}.</strong>
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class LocationChangeEvent extends Event {

    /**
     * The old {@link Location} of the {@link Entity}.
     */
    private final Location oldLocation;

    /**
     * The new {@link Location} of the {@link Entity}.
     */
    private final Location newLocation;

    /**
     * The {@link Entity} changing its {@link Location}.
     */
    private final Entity entity;

    /**
     * Creates a new {@link LocationChangeEvent}.
     *
     * @param oldLocation The old {@link Location} of the {@link Entity}.
     * @param newLocation The new {@link Location} of the {@link Entity}.
     * @param entity The {@link Entity} changing its {@link Location}.
     */
    public LocationChangeEvent(Location oldLocation, Location newLocation, Entity entity) {
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
        this.entity = entity;
    }

    /**
     * @return The old {@link Location} of the {@link Entity}.
     */
    public Location getOldLocation() {
        return oldLocation;
    }

    /**
     * @return The new {@link Location} of the {@link Entity}.
     */
    public Location getNewLocation() {
        return newLocation;
    }

    /**
     * @return The {@link Entity} changing its {@link Location}.
     */
    public Entity getEntity() {
        return entity;
    }
}
