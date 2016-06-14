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
package com.apache.net.msg;

import com.apache.game.entity.player.Player;
import com.apache.net.codec.ByteMessage;

/**
 * An outbound message builder for {@link GameMessage}s. Will build
 * {@link ByteMessage}s which are later converted into {@code GameMessage}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class OutboundGameMessage {

	/**
	 * Builds a {@link ByteMessage} containing the data for this message.
	 *
	 * @param player
	 *            The player.
	 * @return The buffer containing the data.
	 */
	public abstract ByteMessage writeMessage(Player player);

	/**
	 * Converts the {@link ByteMessage} returned by {@code writeMessage(Player)}
	 * to a {@link GameMessage}.
	 *
	 * @param player
	 *            The player.
	 * @return The successfully converted message.
	 */
	public final GameMessage toGameMessage(Player player) {
		ByteMessage msg = writeMessage(player);
		return new GameMessage(msg.getOpcode(), msg.getType(), msg);
	}
}
