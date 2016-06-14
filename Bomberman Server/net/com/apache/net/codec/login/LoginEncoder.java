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
package com.apache.net.codec.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * F {@link MessageToByteEncoder} implementation that encodes and writes the
 * data contained within the {@link LoginResponseMessage} to a buffer that will
 * be sent to the client.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
@Sharable
public final class LoginEncoder extends MessageToByteEncoder<LoginResponseMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, LoginResponseMessage msg, ByteBuf out) throws Exception {
		out.writeByte(msg.getResponse().getOpcode());

		if (msg.getResponse() == LoginResponse.NORMAL) {
			out.writeBoolean(msg.isFlagged());
		}
	}
}