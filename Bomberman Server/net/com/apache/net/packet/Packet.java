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

import io.netty.buffer.ByteBuf;

/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
public class Packet {

	private int opcode;
	private int length;
	private ByteBuf payload;

	public Packet(int opcode, ByteBuf payload) {
		this.opcode = opcode;
		this.payload = payload;
		this.length = payload.readableBytes();
	}

	public int getOpcode() {
		return opcode;
	}

	public ByteBuf getPayload() {
		return payload;
	}

	public int getLength() {
		return length;
	}

}