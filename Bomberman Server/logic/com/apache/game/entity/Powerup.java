/* 
 * This file is property of Apache-GS.
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

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Powerup extends Entity {
    
    private PowerupType type;
    
    public Powerup(PowerupType type){
        this.setType(type);
    }

    @Override
    public void updateSelf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public PowerupType getType() {
		return type;
	}

	public void setType(PowerupType type) {
		this.type = type;
	}
    
}
