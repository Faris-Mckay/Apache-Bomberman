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
package com.client.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
/**
*
* @author JP <https://github.com/TheRealJP>
*/
public class PacketBuilder {

	private int opcode;
	private final ByteBuf payload = Unpooled.buffer();

	public PacketBuilder(int opcode) {
		this.opcode = opcode;
	}

	public void putBytes(byte[] bytes) {
		payload.writeBytes(bytes);
	}

	public void putByte(byte b) {
		payload.writeByte(b);
	}

	public void putInt(int i) {
		payload.writeInt(i);
	}

	public void putString(String str) {
		char[] chars = str.toCharArray();
		for (char c : chars) {
			payload.writeByte((byte) c);
		}
	}

	public static String readString(ByteBuf buffer) {
		StringBuilder builder = new StringBuilder();
		int character = buffer.readUnsignedByte();
		while (buffer.isReadable()) {
			builder.append((char) character);
		}
		return builder.toString();
	}

	public Packet getPacket() {
		return new Packet(opcode, payload);
	}

}