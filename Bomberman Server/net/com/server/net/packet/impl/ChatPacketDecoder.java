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
package com.server.net.packet.impl;

import com.apache.Player;
import com.client.net.packet.Packet;
import com.server.net.packet.PacketDecoder;
import com.server.net.packet.PacketOpcode;
/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
@PacketOpcode({ 2 })
public class ChatPacketDecoder implements PacketDecoder {

	@Override
	public void decode(Player player, Packet packet) {

	}
}
