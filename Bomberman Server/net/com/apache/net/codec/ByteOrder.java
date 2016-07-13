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
 * An enumerated type whose elements represent the possible endianness of game
 * messages.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public enum ByteOrder {

    /**
     * Least significant byte is stored first and the most significant byte is
     * stored last.
     */
    LITTLE,
    /**
     * Most significant byte is stored first and the least significant byte is
     * stored last.
     */
    BIG,
    /**
     * Neither big endian nor little endian, the v1 order.
     */
    MIDDLE,
    /**
     * Neither big endian nor little endian, the v2 order.
     */
    INVERSE_MIDDLE
}
