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
package com.apache.net.codec.login;

/**
 * An enumerated type whose elements represent all of the possible login
 * responses.
 *
 * @author Juan Ortiz <http://github.com/TheRealJP>
 */
public enum LoginResponse {

    NORMAL(2),
    INVALID_CREDENTIALS(3),
    ACCOUNT_BANNED(4),
    ACCOUNT_ONLINE(5),
    SERVER_FULL(7),
    LOGIN_SERVER_OFFLINE(8),
    LOGIN_LIMIT_EXCEEDED(9),
    BAD_SESSION_ID(10),
    PLEASE_TRY_AGAIN(11),
    COULD_NOT_COMPLETE_LOGIN(13),
    LOGIN_ATTEMPTS_EXCEEDED(16);

    /**
     * The response code.
     */
    private final int opcode;

    /**
     * Creates a new {@link LoginResponse}.
     *
     * @param opcode The response code.
     */
    LoginResponse(int opcode) {
        this.opcode = opcode;
    }

    /**
     * @return The response code.
     */
    public final int getOpcode() {
        return opcode;
    }
}
