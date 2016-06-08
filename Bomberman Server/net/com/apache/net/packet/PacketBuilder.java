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
package com.apache.net.packet;

import com.apache.util.Utility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
public class PacketBuilder {

	private int opcode;

	private final ByteBuf payload = Unpooled.buffer();

	public PacketBuilder() {
		this(-1);
	}

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

	public void writeArray(ByteBuf buff, int... vals) {
		for (int i = 0; i < vals.length; i++) {
			buff.writeByte(vals[i]);
		}
	}

	public void writeArray(ByteBuf buff, byte... vals) {
		for (int i = 0; i < vals.length; i++) {
			buff.writeByte(vals[i]);
		}
	}

	public void writeS(ByteBuf buff, String value) {
		if (value == null)
			throw new RuntimeException("Value is null!");

		try {
			for (int i = 0; i < value.length(); i++) {
				buff.writeChar(value.charAt(i));
			}
			buff.writeChar('\000');
		} catch (Exception e) {
			Utility.log("Failed writing string!" + e);
		}
	}

	public void putString(String str) {
		char[] chars = str.toCharArray();
		for (char c : chars) {
			payload.writeByte((byte) c);
		}
	}

	/** Reads the string buffer. */
	public static String readString(ByteBuf buffer) {
		StringBuilder builder = new StringBuilder();
		while (buffer.isReadable()) {
			builder.append((char) buffer.readUnsignedByte());
		}
		return builder.toString();
	}

	/** Converts data to a single packet. */
	public Packet toPacket() {
		return new Packet(opcode, payload);
	}
}
