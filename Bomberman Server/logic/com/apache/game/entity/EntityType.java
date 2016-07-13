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
package com.apache.game.entity;

/**
 * An enumerated type whose elements represent all of the types an
 * {@link Entity} can be.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public enum EntityType {

    /**
     * An enhancer item that can be picked up by a {@link Player}.
     */
    POWERUP,
    /**
     * An {@link Entity} that is controlled by a real person.
     */
    PLAYER,
    /**
     * An object that can be destroyed up by a {@link Player}.
     */
    BRICK,
    /**
     * An {@link Entity} that is controlled by the game itself (Artificially
     * Intelligent Player).
     */
    NPC;
}
