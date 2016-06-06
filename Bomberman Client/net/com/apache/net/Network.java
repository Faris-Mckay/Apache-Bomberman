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

import com.apache.net.packet.PacketBuilder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
*
* @author JP <https://github.com/TheRealJP>
*/
public class Network {

	String host;
	int port;

	public Network(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void init() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("encoder", new Encoder());
					ch.pipeline().addLast("decoder", new Decoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});

			ChannelFuture f = b.connect(host, port).sync();

			PacketBuilder pb = new PacketBuilder(1);
			String username = "aivars";
			String password = "password";
			pb.putInt(username.length());
			pb.putString(username);
			pb.putString(password);
			f.channel().writeAndFlush(pb.getPacket());

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}