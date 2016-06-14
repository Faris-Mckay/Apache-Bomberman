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
package com.apache.net.codec.game;

import com.apache.net.codec.IsaacCipher;
import com.apache.net.codec.MessageType;
import com.apache.net.msg.GameMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * F {@link MessageToByteEncoder} implementation that encodes all
 * {@link GameMessage}s into {@link ByteBuf}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class GameMessageEncoder extends MessageToByteEncoder<GameMessage> {

	/**
	 * The encryptor for this message.
	 */
	private final IsaacCipher encryptor;

	/**
	 * Creates a new {@link GameMessageEncoder}.
	 *
	 * @param encryptor
	 *            The encryptor for this encoder.
	 */
	public GameMessageEncoder(IsaacCipher encryptor) {
		this.encryptor = encryptor;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, GameMessage msg, ByteBuf out) throws Exception {
		out.writeByte(msg.getOpcode() + encryptor.nextInt());
		if (msg.getType() == MessageType.VARIABLE) {
			out.writeByte(msg.getSize());
		} else if (msg.getType() == MessageType.VARIABLE_SHORT) {
			out.writeShort(msg.getSize());
		}
		out.writeBytes(msg.getPayload().getBuffer());

		msg.getPayload().release();
	}
}
