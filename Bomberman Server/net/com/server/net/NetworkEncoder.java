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
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class NetworkEncoder extends MessageToMessageEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
		System.out.println("encoding");
		int headerLength = 3;
		int payloadLength = packet.getLength();
		ByteBuf buffer = Unpooled.buffer(headerLength + payloadLength);
		buffer.writeByte(packet.getOpcode());
		// buffer.writeByte(payloadLength);
		buffer.writeBytes(packet.getPayload());
		out.add(buffer);
	}
}