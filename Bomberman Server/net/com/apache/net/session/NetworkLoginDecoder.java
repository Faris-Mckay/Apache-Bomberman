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
package com.apache.net.session;

import java.util.List;

import com.apache.game.Lobby;
import com.apache.game.entity.Player;
import com.apache.net.packet.PacketBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
public class NetworkLoginDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// System.out.println("NetworkLoginDecoder");
		int opcode = in.readByte();
		if (opcode == 1) {
			ByteBuf payload = in.readBytes(in.readableBytes());
			int usernameLength = payload.readInt();
			String username = PacketBuilder.readString(payload.readBytes(usernameLength));
			String password = PacketBuilder.readString(payload.readBytes(payload.readableBytes()));

			Player player = new Player(ctx.channel(), username, password);

			if (!Lobby.getLobby().getPlayers().add(player)) {
				System.out.println("Could not register player : " + player + " [world full]");
			}
			ctx.channel().pipeline().replace("decoder", "decoder", new NetworkGameDecoder());
			Lobby.getLobby().load(player);
			System.out.println(player.getUsername() + " has successfully connected.");

		}
	}
}
