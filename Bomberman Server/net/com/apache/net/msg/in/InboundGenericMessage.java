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

import com.apache.event.Event;
import com.apache.game.entity.player.Player;
import com.apache.net.msg.GameMessage;
import com.apache.net.msg.InboundGameMessage;

/**
 * An {@link InboundGameMessage} implementation that serves as the default
 * message handler. It does nothing when executed.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class InboundGenericMessage extends InboundGameMessage {

	@Override
	public Event readMessage(Player player, GameMessage msg) throws Exception {
		return null;
	}
}
