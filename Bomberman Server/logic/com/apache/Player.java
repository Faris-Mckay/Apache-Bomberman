/* 
 * This file is part of Bomberman.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache;

import com.apache.map.Location;
import java.util.List;

public class Player {
    
    private String name;
    private Location location;
    private boolean isAlive;
    private int kills;
    
    public Player(String name){
        this.name = name;
        this.isAlive = true;
    }
    
    public void update(List<Player> playersToUpdate){
        
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
     * @return the isAlive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @param isAlive the isAlive to set
     */
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * @return the kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * add one to the kills
     */
    public void addKill() {
        this.kills = ++kills;
    }

}
