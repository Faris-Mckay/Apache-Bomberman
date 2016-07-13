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
package com.apache.map;

import java.util.Random;

import com.apache.game.entity.powerup.PowerupType;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Brick {

    private Location location;
    private PowerupType type;

    public Brick(Location location) {
        this.setLocation(location);
        addPower();
    }

    private void addPower() {
        int random = new Random().nextInt(3);
        switch (random) {
            case 0:
                setType(PowerupType.BOMB);
                break;
            case 1:
                setType(PowerupType.BOOST);
                break;
            case 2:
                setType(PowerupType.SPEED);
                break;
            default:
                setType(PowerupType.NONE);
                break;
        }
    }

    /**
     * @return the type
     */
    public PowerupType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PowerupType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
