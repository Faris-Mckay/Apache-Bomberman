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
package com.apache.net.packet.impl;

import java.util.logging.Logger;

import com.apache.Player;
import com.apache.net.packet.Packet;
import com.apache.net.packet.PacketDecoder;
import com.apache.net.packet.PacketOpcode;
/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
@PacketOpcode({ 0 })
public class DefaultPacketDecoder implements PacketDecoder {

	private static final Logger logger = Logger.getLogger(DefaultPacketDecoder.class.getName());

	@Override
	public void decode(Player player, Packet packet) {
		logger.warning("Unhandled Packet : [opcode=" + packet.getOpcode() + " length=" + packet.getLength() + "]");
	}

}
