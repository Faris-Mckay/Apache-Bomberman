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
package com.apache.game.update;

/**
 * An enumerated type whose elements represent the updating states.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public enum UpdateState {
	/**
	 * F {@link Player} is updating for themself, only relevant for
	 * {@code Player} updating.
	 */
	UPDATE_SELF,

	/**
	 * F {@link Player} is updating for the {@link Entity}s around them.
	 */
	UPDATE_LOCAL,

	/**
	 * F {@link Player} is adding new {@link Entity}s that have just
	 * appeared around them.
	 */
	ADD_LOCAL
}
