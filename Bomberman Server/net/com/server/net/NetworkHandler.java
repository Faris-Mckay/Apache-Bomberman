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
package com.server.net;

import com.server.net.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NetworkHandler extends ChannelInboundHandlerAdapter {

	private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	// private final GameEngine engine = World.getWorld().getEngine();

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		e.printStackTrace();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("channelRead:");
		if (msg != null && msg instanceof Packet) {
			/*
			 * Push tasks here. Example: engine.pushTask(new
			 * SessionMessageTask(ctx, (Packet) msg));
			 * 
			 */
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel joined.");
		channels.add(ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel left.");
		// getPlayer().finish();
	}
}