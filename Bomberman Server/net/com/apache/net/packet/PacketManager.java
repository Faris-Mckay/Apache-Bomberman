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

import java.io.File;

import com.apache.game.entity.Player;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Juan Ortiz <https://github.com/TheRealJP>
 */
public class PacketManager {

	private static final PacketManager INSTANCE = new PacketManager();

	public static PacketManager getPacketManager() {
		return INSTANCE;
	}

	private static PacketDecoder[] packetDecoders = new PacketDecoder[256];

	public void bind(int id, PacketDecoder decoder) {
		packetDecoders[id] = decoder;
	}

	public void handle(ChannelHandlerContext session, Packet packet) {
		try {
			packetDecoders[packet.getOpcode()].decode((Player) session.read(), packet);
		} catch (Exception ex) {
			System.out.println("Exception handling packet: " + ex);
			session.channel().close();
		}
	}

	public static void loadPacketDecoders() throws Exception {
		File[] files = new File("./net/com/apache/net/packet/impl/").listFiles();
		for (File file : files) {
			Class<?> c = Class.forName("com.apache.net.packet.impl." + file.getName().replaceAll(".java", ""));

			if (PacketDecoder.class.isAssignableFrom(c) && !c.isInterface()) {
				PacketDecoder packet = (PacketDecoder) c.newInstance();
				if (packet.getClass().getAnnotation(PacketOpcode.class) == null) {
					throw new Exception("PacketOpcode missing:" + packet);
				}

				int packetOpcodes[] = packet.getClass().getAnnotation(PacketOpcode.class).value();

				for (int opcode : packetOpcodes) {
					PacketManager.getPacketManager().bind(opcode, packet);
					System.out.println("Bound " + packet.toString() + " to opcode : " + opcode);
				}
			}
		}
	}
}
