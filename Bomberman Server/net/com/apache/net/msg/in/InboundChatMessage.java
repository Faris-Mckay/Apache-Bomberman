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
package com.apache.net.msg.in;

import static com.google.common.base.Preconditions.checkState;

import com.apache.event.Event;
import com.apache.event.impl.ChatEvent;
import com.apache.game.chat.Chat;
import com.apache.game.entity.player.Player;
import com.apache.net.codec.ByteTransform;
import com.apache.net.msg.GameMessage;
import com.apache.net.msg.InboundGameMessage;

/**
 * An {@link InboundGameMessage} implementation that decodes data sent when a
 * {@link Player} manually talks.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class InboundChatMessage extends InboundGameMessage {

	@Override
	public Event readMessage(Player player, GameMessage msg) throws Exception {
		int effects = msg.getPayload().get(false);
		int color = msg.getPayload().get(false);
		int size = (msg.getSize() - 2);
		byte[] message = msg.getPayload().getBytesReverse(size, ByteTransform.F);

		checkState(effects >= 0, "invalid effects value");
		checkState(color >= 0, "invalid color value");
		checkState(size > 0, "invalid size, not large enough");

		player.chat(new Chat(message, color, effects));
		return new ChatEvent(effects, color, size, message);
	}
}
