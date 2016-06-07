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
package com.apache.game.entity;

import com.apache.map.Location;
import java.util.List;

public class Player extends Entity {
    
    private String name;
    
    private int kills;
    
    public Player(String name){
        this.name = name;
        setAlive(true);
    }
    
    public void updateOthers(List<Player> playersToUpdate){
        
    }
    
    @Override
    public void updateSelf(){
        
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
