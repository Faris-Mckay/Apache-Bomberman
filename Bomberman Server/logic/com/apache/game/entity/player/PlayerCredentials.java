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
package com.apache.game.entity.player;

import com.apache.util.StringUtility;

/**
 * An immutable class that holds the credentials for a {@link Player}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class PlayerCredentials {

    /**
     * The username credential.
     */
    private final String username;

    /**
     * The password credential.
     */
    private final String password;

    /**
     * The username hash credential, generated from the {@code username}.
     */
    private final long usernameHash;

    /**
     * Creates a new {@link PlayerCredentials}.
     *
     * @param username The username credential.
     * @param password The password credential.
     */
    public PlayerCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        usernameHash = StringUtility.encode(username);
    }

    /**
     * @return The username credential.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password credential.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return The username hash.
     */
    public long getUsernameHash() {
        return usernameHash;
    }
}
