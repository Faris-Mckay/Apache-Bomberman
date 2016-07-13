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

import com.apache.net.codec.IsaacCipher;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * An immutable upstream Netty message that contains the decoded data from the
 * login protocol.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class LoginCredentialsMessage {

    /**
     * The username of the player.
     */
    private final String username;

    /**
     * The password of the player.
     */
    private final String password;

    /**
     * The encryptor for encrypting game messages.
     */
    private final IsaacCipher encryptor;

    /**
     * The decryptor for decrypting game messages.
     */
    private final IsaacCipher decryptor;

    /**
     * The pipeline for the underlying {@link Channel}.
     */
    private final ChannelPipeline pipeline;

    /**
     * Creates a new {@link LoginCredentialsMessage}.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @param encryptor The encryptor for encrypting game messages.
     * @param decryptor The decryptor for decrypting game messages.
     */
    public LoginCredentialsMessage(String username, String password, IsaacCipher encryptor, IsaacCipher decryptor,
            ChannelPipeline pipeline) {
        this.username = username;
        this.password = password;
        this.encryptor = encryptor;
        this.decryptor = decryptor;
        this.pipeline = pipeline;
    }

    /**
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The password of the player.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return The encryptor for encrypting game messages.
     */
    public IsaacCipher getEncryptor() {
        return encryptor;
    }

    /**
     * @return The decryptor for decrypting game messages.
     */
    public IsaacCipher getDecryptor() {
        return decryptor;
    }

    /**
     * @return The pipeline for the underlying {@link Channel}.
     */
    public ChannelPipeline getPipeline() {
        return pipeline;
    }
}
