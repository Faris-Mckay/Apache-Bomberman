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

import com.apache.game.entity.player.Player;
import com.apache.net.codec.ByteMessage;
import com.apache.net.msg.OutboundGameMessage;

/**
 * An {@link OutboundGameMessage} implementation that disposes the login
 * session.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class SendLogoutMessage extends OutboundGameMessage {

    @Override
    public ByteMessage writeMessage(Player player) {
        return ByteMessage.message(2);
    }
}
