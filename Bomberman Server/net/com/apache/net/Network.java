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

import com.apache.net.packet.PacketManager;
import com.apache.net.session.NetworkLoginDecoder;
import com.apache.util.Utility;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author JP <https://github.com/TheRealJP>
 */
public final class Network extends ChannelInboundHandlerAdapter {

	private ServerBootstrap bootstrap;
	private ChannelFuture channelFuture;
	private int port;

	public Network(int port) throws Exception {
		this.port = port;
		if (bootstrap == null) {
			load();
		}
		PacketManager.loadPacketDecoders();
	}

	public void load() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("decoder", new NetworkLoginDecoder());
							ch.pipeline().addLast("encoder", new NetworkEncoder());
							ch.pipeline().addLast(new NetworkHandler());
						}
					}).childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (channelFuture != null) {
			channelFuture.channel().close();
		}
	}

	public void run() {
		try {
			channelFuture = bootstrap.bind(port);
			Utility.log("Network started on[Port: " + port + "]");
			channelFuture.sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			Utility.log("Network thread fail!" + e);
		} finally {
			bootstrap.config().group().shutdownGracefully();
			bootstrap.config().childGroup().shutdownGracefully();
			bootstrap = null;
			channelFuture = null;
		}
	}

}