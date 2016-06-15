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
package com.apache.game.entity.powerup;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public enum PowerupType {
    
    /**
     * Increase the speed of the players movement
     * (Update that player more times per cycle)
     */
    SPEED,
    
    /**
     * Increase the power of the players bombs
     */
    BOOST,
    
    /**
     * Increase the amount of bombs the player is allowed to drop at one
     * time
     */
    BOMB,
    
    /**
     * Contains no power up at all
     */
    NONE
    
}
