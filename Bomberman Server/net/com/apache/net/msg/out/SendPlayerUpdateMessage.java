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
package com.apache.net.msg.out;

import java.util.Iterator;

import com.apache.game.entity.player.Player;
import com.apache.game.update.PlayerChatUpdateBlock;
import com.apache.game.update.UpdateBlock;
import com.apache.game.update.UpdateBlockSet;
import com.apache.game.update.UpdateState;
import com.apache.net.codec.ByteMessage;
import com.apache.net.codec.MessageType;
import com.apache.net.msg.OutboundGameMessage;

/**
 * An {@link OutboundMessageWriter} implementation that sends an update message
 * containing the underlying {@link Player} and other {@code Player}s
 * surrounding them.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class SendPlayerUpdateMessage extends OutboundGameMessage {

	/**
	 * The {@link UpdateBlockSet} that will manage all of the
	 * {@link UpdateBlock}s.
	 */
	private final UpdateBlockSet<Player> blockSet = new UpdateBlockSet<>();

	{
		blockSet.add(new PlayerChatUpdateBlock());

	}

	@Override
	public ByteMessage writeMessage(Player player) {
		ByteMessage msg = ByteMessage.message(81, MessageType.VARIABLE_SHORT);
		ByteMessage blockMsg = ByteMessage.message();

		try {
			msg.startBitAccess();

			handleMovement(player, msg);
			blockSet.encodeUpdateBlocks(player, blockMsg, UpdateState.UPDATE_SELF);

			msg.putBits(8, player.getWorld().getPlayers().size());
			Iterator<Player> $it = player.getWorld().getPlayers().iterator();
			while ($it.hasNext()) {
				Player other = $it.next();
				handleMovement(other, msg);
				blockSet.encodeUpdateBlocks(other, blockMsg, UpdateState.UPDATE_LOCAL);
			}

			if (blockMsg.getBuffer().writerIndex() > 0) {
				msg.putBits(11, 2047);
				msg.endBitAccess();
				msg.putBytes(blockMsg);
			} else {
				msg.endBitAccess();
			}
		} catch (Exception e) {
			msg.release();
			throw e;
		} finally {
			blockMsg.release();
		}
		return msg;
	}

	/**
	 * Adds {@code addPlayer} in the view of {@code player}.
	 *
	 * @param msg
	 *            The main update message.
	 * @param player
	 *            The {@link Player} this update message is being sent for.
	 * @param addPlayer
	 *            The {@code Player} being added.
	 */
	private void addPlayer(ByteMessage msg, Player player, Player addPlayer) {
		msg.putBits(11, addPlayer.getId()); // username index of 12 (0-11)
		msg.putBit(true); // TODO: make use of client caching
		msg.putBit(true);

		int deltaX = addPlayer.getLocation().getX() - player.getLocation().getX();
		int deltaY = addPlayer.getLocation().getY() - player.getLocation().getY();
		msg.putBits(5, deltaY);
		msg.putBits(5, deltaX);
	}

	/**
	 * Handles running, walking, and teleportation movement for {@code player}.
	 *
	 * @param player
	 *            The {@link Player} to handle running and walking for.
	 * @param msg
	 *            The main update message.
	 */
	private void handleMovement(Player player, ByteMessage msg) {
		boolean needsUpdate = !player.getUpdateFlags().isEmpty();

		// Logic here
	}
}
