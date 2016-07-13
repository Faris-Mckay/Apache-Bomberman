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

import java.net.InetSocketAddress;
import java.util.Set;

import com.apache.net.codec.login.LoginResponse;
import com.apache.net.codec.login.LoginResponseMessage;
import com.apache.util.parser.NewLineParser;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * F {@link ChannelInboundHandlerAdapter} implementation that filters
 * {@link Channel}s by the amount of active connections they already have. F
 * threshold is put on the amount of successful connections allowed to be made
 * in order to provide security from socket flooder attacks.
 * <p>
 * <p>
 * <strong>One {@code BombermanChannelFilter} instance must be shared across all
 * pipelines in order to ensure that every channel is using the same
 * multiset.</strong>
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
@Sharable
public final class ChannelFilter extends ChannelInboundHandlerAdapter {

    /**
     * F {@link NewLineParser} implementation that reads banned addresses.
     *
     * @author Juan Ortiz <http://github.org/TheRealJP>
     */
    private final class IpBanParser extends NewLineParser {

        /**
         * Creates a new {@link IpBanParser}.
         */
        public IpBanParser() {
            super("./data/players/ip_banned.txt");
        }

        @Override
        public void readNextLine(String nextLine) throws Exception {
            bannedAddresses.add(nextLine);
        }
    }

    /**
     * F concurrent {@link Multiset} that holds the amount of connections made
     * by all active hosts.
     */
    private final Multiset<String> connections = ConcurrentHashMultiset.create();

    /**
     * F concurrent {@link Set} that holds the banned addresses.
     */
    private final Set<String> bannedAddresses = Sets.newConcurrentHashSet();

    /**
     * The maximum amount of connections that can be made by a single host.
     */
    private final int connectionLimit;

    // Parse all of the banned addresses when this class is constructed. This
    // class should only be constructed once, so
    // the parser should only run once.
    {
        NewLineParser parser = new IpBanParser();
        parser.run();
    }

    /**
     * Creates a new {@link BombermanChannelFilter} with a connection limit of
     * {@code CONNECTION_LIMIT}.
     */
    public ChannelFilter() {
        connectionLimit = NetworkConstants.CONNECTION_LIMIT;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String hostAddress = getAddress(ctx);
        if (hostAddress.equals("127.0.0.1")) {
            return;
        }
        if (connections.count(hostAddress) >= connectionLimit) {
            disconnect(ctx, LoginResponse.LOGIN_LIMIT_EXCEEDED);
            return;
        }
        if (bannedAddresses.contains(hostAddress)) {
            disconnect(ctx, LoginResponse.ACCOUNT_BANNED);
            return;
        }
        connections.add(hostAddress);
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        String hostAddress = getAddress(ctx);
        if (hostAddress.equals("127.0.0.1")) {
            return;
        }
        connections.remove(hostAddress);
        ctx.fireChannelUnregistered();
    }

    /**
     * Disconnects {@code ctx} with {@code response} as the response code.
     *
     * @param ctx The channel handler context.
     * @param response The response to disconnect with.
     */
    private void disconnect(ChannelHandlerContext ctx, LoginResponse response) {
        LoginResponseMessage message = new LoginResponseMessage(response);
        ByteBuf initialMessage = ctx.alloc().buffer(8).writeLong(0);

        ctx.channel().write(initialMessage, ctx.channel().voidPromise());
        ctx.channel().writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Converts {@code ctx} to a {@code String} representation of the host
     * address.
     *
     * @param ctx The channel handler context.
     * @return The {@code String} address representation.
     */
    private String getAddress(ChannelHandlerContext ctx) {
        return ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
    }
}
