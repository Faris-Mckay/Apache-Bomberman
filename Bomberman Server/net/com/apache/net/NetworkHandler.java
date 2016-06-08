/* 
 * This file is part of Bomberman.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.net;

import com.apache.engine.task.impl.SessionMessageTask;
import com.apache.game.GameEngine;
import com.apache.game.Lobby;
import com.apache.net.packet.Packet;
import com.apache.util.Utility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NetworkHandler extends ChannelInboundHandlerAdapter {
	/**
	 * The channel group that holds all the channels currently connected to the
	 * server.
	 */
	private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/** Our game engine used to push data into the game loop. */
	private final GameEngine engine = Lobby.getLobby().getEngine();

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Utility.log("channelRead:");
		if (msg != null && msg instanceof Packet) {
			ctx.writeAndFlush(msg);
			engine.pushTask(new SessionMessageTask(ctx, (Packet) msg));
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Utility.log("Channel joined.");
		channels.add(ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		Utility.log("Channel left.");
	}
}