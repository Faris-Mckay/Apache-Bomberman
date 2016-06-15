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
package com.apache.game.update;

import static com.google.common.base.Preconditions.checkState;

import java.util.LinkedHashSet;
import java.util.Set;

import com.apache.game.entity.Entity;
import com.apache.game.entity.EntityType;
import com.apache.game.entity.player.Player;
import com.apache.game.update.UpdateFlagContainer.UpdateFlag;
import com.apache.net.codec.ByteMessage;
import com.apache.net.codec.ByteOrder;

/**
 * F group of {@link UpdateBlock}s that will be encoded and written to the main
 * update buffer.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class UpdateBlockSet<E extends Entity> {

	/**
	 * An ordered {@link Set} containing all of the {@link UpdateBlock}s that
	 * can be encoded.
	 */
	private final Set<UpdateBlock<E>> updateBlocks = new LinkedHashSet<>();

	/**
	 * Adds an {@link UpdateBlock} to this {@code UpdateBlockSet}. Throws an
	 * {@link IllegalStateException} if this {@code
	 * UpdateBlockSet} already contains {@code block}.
	 *
	 * @param block
	 *            The {@link UpdateBlock} to add.
	 */
	public void add(UpdateBlock<E> block) {
		checkState(updateBlocks.add(block), "updateBlocks.contains(block)");
	}

	/**
	 * Encodes the update blocks for {@code forMob} and appends the data to
	 * {@code msg}.
	 *
	 * @param forMob
	 *            The {@link MobileEntity} to encode update blocks for.
	 * @param msg
	 *            The main update buffer.
	 * @param state
	 *            The {@link UpdateState} that the underlying {@link Player} is
	 *            in.
	 */
	public void encodeUpdateBlocks(E forMob, ByteMessage msg, UpdateState state) {
		if (forMob.getUpdateFlags().isEmpty() && state != UpdateState.ADD_LOCAL) {
			return;
		}

		if (forMob.type() == EntityType.PLAYER) {
			encodePlayerBlocks(forMob, msg, state);
		} else if (forMob.type() == EntityType.NPC) {
			encodeNpcBlocks(forMob, msg, state);
		} else {
			throw new IllegalStateException("forMob.type() must be PLAYER or NPC");
		}
	}

	/**
	 * Encodes update blocks specifically for a {@link Player}.
	 *
	 * @param forMob
	 *            The {@code Player} to encode update blocks for.
	 * @param msg
	 *            The main update buffer.
	 * @param state
	 *            The {@link UpdateState} that the underlying {@code Player} is
	 *            in.
	 */
	private void encodePlayerBlocks(E forMob, ByteMessage msg, UpdateState state) {
		Player player = (Player) forMob;
		boolean cacheBlocks = (state != UpdateState.ADD_LOCAL && state != UpdateState.UPDATE_SELF);

		if (player.getCachedBlock() != null && cacheBlocks) {
			msg.putBytes(player.getCachedBlock());
			return;
		}

		ByteMessage encodedBlocks = encodeBlocks(forMob, state);
		msg.putBytes(encodedBlocks);
		if (cacheBlocks) {
			player.setCachedBlock(encodedBlocks);
		} else {
			encodedBlocks.release();
		}
	}

	/**
	 * Encodes update blocks specifically for a {@link Npc}.
	 *
	 * @param forMob
	 *            The {@code Npc} to encode update blocks for.
	 * @param msg
	 *            The main update buffer.
	 * @param state
	 *            The {@link UpdateState} that the underlying {@code Npc} is in.
	 */
	private void encodeNpcBlocks(E forMob, ByteMessage msg, UpdateState state) {
		ByteMessage encodedBlocks = encodeBlocks(forMob, state);
		msg.putBytes(encodedBlocks);

		encodedBlocks.release();
	}

	/**
	 * Encodes the {@link UpdateBlock}s for {@code forMob} and returns the
	 * buffer containing the data.
	 *
	 * @param forMob
	 *            The {@link MobileEntity} to encode for.
	 * @param state
	 *            The {@link UpdateState} that the underlying {@link Player} is
	 *            in.
	 * @return The buffer containing the data.
	 */
	private ByteMessage encodeBlocks(E forMob, UpdateState state) {
		ByteMessage encodedBlock = ByteMessage.message();

		int mask = 0;
		Set<UpdateBlock<E>> writeBlocks = new LinkedHashSet<>();

		for (UpdateBlock<E> updateBlock : updateBlocks) {
			if (state == UpdateState.ADD_LOCAL && updateBlock.getFlag() == UpdateFlag.GRAPHIC) {
				mask |= updateBlock.getMask();
				writeBlocks.add(updateBlock);
				continue;
			}
			if (state == UpdateState.UPDATE_SELF && updateBlock.getFlag() == UpdateFlag.CHAT) {
				continue;
			}
			if (!forMob.getUpdateFlags().get(updateBlock.getFlag())) {
				continue;
			}

			mask |= updateBlock.getMask();
			writeBlocks.add(updateBlock);
		}

		if (mask >= 0x100) {
			mask |= 0x40;
			encodedBlock.putShort(mask, ByteOrder.LITTLE);
		} else {
			encodedBlock.put(mask);
		}

		writeBlocks.forEach(it -> it.write(forMob, encodedBlock));
		return encodedBlock;
	}
}