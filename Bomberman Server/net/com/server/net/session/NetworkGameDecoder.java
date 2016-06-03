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
package com.server.net.session;

import java.util.List;

import com.client.net.packet.Packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
public class NetworkGameDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("NetworkGameDecoder");
		if (in.isReadable()) {
			int opcode = in.readByte();
			if (opcode > 0) {
				ByteBuf payload = in.readBytes(in.readableBytes());
				Packet packet = new Packet(opcode, payload);
				out.add(packet);
			}
		}
	}
}