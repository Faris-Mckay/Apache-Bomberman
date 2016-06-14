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
package com.apache.net.codec;

/**
 * An enumerated type whose elements represent the possible {@link ByteMessage}
 * types.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public enum MessageType {

	/**
	 * Represents a non-game packet of data.
	 */
	RAW,

	/**
	 * Represents a fixed length game packet.
	 */
	FIXED,

	/**
	 * Represents a variable byte length game packet.
	 */
	VARIABLE,

	/**
	 * Represents a variable short length game packet.
	 */
	VARIABLE_SHORT
}
