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
package com.apache.net.session;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

import static com.google.common.base.Preconditions.checkState;

/**
 * An abstraction model that determines how I/O operations are handled for a
 * {@link Player}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public class Session {

	/**
	 * The {@link Channel} to send and receive messages through.
	 */
	private final Channel channel;

	/**
	 * The address that the connection was received from.
	 */
	private final String hostAddress;

	/**
	 * Creates a new {@link Session}.
	 *
	 * @param channel
	 *            The {@link Channel} to send and receive messages through.
	 */
	public Session(Channel channel) {
		this.channel = channel;
		this.hostAddress = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
	}

	/**
	 * Disposes of this {@code Session} by closing the {@link Channel} and
	 * executing the {@code onDispose()} listener.
	 */
	public final void dispose() {
		Channel channel = getChannel();
		checkState(!channel.isActive(), "call getChannel().close() instead!");

		onDispose();
	}

	/**
	 * Executed when this {@link Session} needs to be disposed of.
	 */
	public void onDispose() {

	}

	/**
	 * Implementations decide which messages are handled and how they are
	 * handled. Messages are ignored completely by default.
	 *
	 * @param msg
	 *            The message to handle.
	 */
	public void handleUpstreamMessage(Object msg) throws Exception {
	}

	/**
	 * @return The {@link Channel} to send and receive messages through.
	 */
	public final Channel getChannel() {
		return channel;
	}

	/**
	 * @return The address that the connection was received from.
	 */
	public final String getHostAddress() {
		return hostAddress;
	}
}