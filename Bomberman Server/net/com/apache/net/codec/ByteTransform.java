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
 * An enumerated type whose elements represent the possible custom Bomberman
 * value types.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public enum ByteTransform {

    /**
     * Do nothing to the value.
     */
    DEFAULT,
    /**
     * Add {@code 128} to the value. (Faris)
     */
    F,
    /**
     * Invert the sign of the value. (Matt)
     */
    M,
    /**
     * Subtract {@code 128} from the value. (Jack)
     */
    J
}
