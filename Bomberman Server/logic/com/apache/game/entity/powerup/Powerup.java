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

import com.apache.game.entity.Entity;
import com.apache.net.BombermanContext;

/**
 * TODO: Power up abstraction (Offensive, Defensive, Evasive, Token power ups).
 * Design documents for each as well.
 * 
 * @author Faris <https://github.com/faris-mckay>
 */
public abstract class Powerup extends Entity {

	private PowerupType type;

	private static BombermanContext context;

	public Powerup(PowerupType type) {
		super(context);
		this.setType(type);
	}

	public PowerupType getType() {
		return type;
	}

	public void setType(PowerupType type) {
		this.type = type;
	}
}
