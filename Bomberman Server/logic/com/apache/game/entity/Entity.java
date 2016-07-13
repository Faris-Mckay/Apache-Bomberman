/* 
 * This file is property of Apache-GS.
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
package com.apache.game.entity;

import com.apache.game.GameService;
import com.apache.game.World;
import com.apache.game.entity.attrib.AttributeMap;
import com.apache.game.update.UpdateFlagContainer;
import com.apache.map.Location;
import com.apache.net.BombermanContext;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public abstract class Entity {

    /**
     * The {@link BombermanContext} dedicated to this {@code Entity}.
     */
    protected final BombermanContext context;

    /**
     * The {@link GameService} dedicated to this {@code Entity}.
     */
    protected final GameService service;

    /**
     * The {@link World} dedicated to this {@code Entity}.
     */
    protected final World world;

    /**
     * An {@link UpdateFlagHolder} instance assigned to this
     * {@code MobileEntity}.
     */
    protected final UpdateFlagContainer updateFlags = new UpdateFlagContainer();

    /**
     * An {@link AttributeMap} instance assigned to this {@code Entity}.
     */
    public final AttributeMap attributes = new AttributeMap();

    private int id;
    /**
     * The location of this {@code Entity}.
     */
    private Location location;

    /**
     * Creates a new {@link Entity}.
     *
     * @param context The context to be managed in.
     */
    public Entity(BombermanContext context) {
        this.context = context;
        service = context.getService();
        world = context.getWorld();
    }

    /**
     * @return The {@code EntityType} designated for this {@code Entity}.
     */
    public abstract EntityType type();

    /**
     * @return The {@link BombermanContext} dedicated to this {@code Entity}.
     */
    public BombermanContext getContext() {
        return context;
    }

    /**
     * @return The {@link GameService} dedicated to this {@code Entity}.
     */
    public final GameService getService() {
        return service;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return The {@link AttributeMap} instance assigned to this
     * {@code Entity}.
     */
    public final AttributeMap attr() {
        return attributes;
    }

    /**
     * @return The {@link UpdateFlagContainer} instance assigned to this
     * {@code Entity}.
     */
    public final UpdateFlagContainer getUpdateFlags() {
        return updateFlags;
    }

    /**
     * @return The {@link World} dedicated to this {@code Entity}.
     */
    public final World getWorld() {
        return world;
    }

    /**
     * @return the entity's current id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the entity's id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
