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
package com.apache.net;

import com.apache.net.codec.login.LoginDecoder;
import com.apache.net.codec.login.LoginEncoder;
import com.apache.net.msg.MessageRepository;
import com.apache.net.session.Session;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * The {@link ChannelInitializer} implementation that will initialize
 * {@link SocketChannel}s before they are registered.
 *
 * @author Juan Ortiz <http://github.com/TheRealJP>
 */
@Sharable
public final class BombermanChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * Handles upstream messages from Netty.
     */
    private final ChannelHandler upstreamHandler = new BombermanUpstreamHandler();

    /**
     * Encodes the login response.
     */
    private final ChannelHandler loginEncoder = new LoginEncoder();

    /**
     * Filters channels based on the amount of active connections they have.
     */
    public final ChannelHandler channelFilter = new BombermanChannelFilter();

    /**
     * The underlying context to be managed under.
     */
    private final BombermanContext context;

    /**
     * The repository containing data for incoming messages.
     */
    private final MessageRepository messageRepository;

    /**
     * Creates a new {@link BombermanChannelInitializer}.
     *
     * @param context The underlying context to be managed under.
     * @param messageRepository The repository containing data for incoming
     * messages.
     */
    public BombermanChannelInitializer(BombermanContext context, MessageRepository messageRepository) {
        this.context = context;
        this.messageRepository = messageRepository;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.attr(NetworkConstants.SESSION_KEY).setIfAbsent(new Session(ch));

        ch.pipeline().addLast("read-timeout", new ReadTimeoutHandler(NetworkConstants.READ_IDLE_SECONDS));
        ch.pipeline().addLast("channel-filter", channelFilter);
        ch.pipeline().addLast("login-decoder", new LoginDecoder(context, messageRepository));
        ch.pipeline().addLast("login-encoder", loginEncoder);
        ch.pipeline().addLast("upstream-handler", upstreamHandler);
    }

}
