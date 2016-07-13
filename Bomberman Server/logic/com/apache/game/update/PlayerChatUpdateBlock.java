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

import com.apache.game.chat.Chat;
import com.apache.game.entity.player.Player;
import com.apache.game.update.UpdateFlagContainer.UpdateFlag;
import com.apache.net.codec.ByteMessage;
import com.apache.net.codec.ByteOrder;
import com.apache.net.codec.ByteTransform;

/**
 * An {@link PlayerUpdateBlock} implementation that handles the {@link Chat}
 * update block.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class PlayerChatUpdateBlock extends PlayerUpdateBlock {

    /**
     * Creates a new {@link PlayerChatUpdateBlock}.
     */
    public PlayerChatUpdateBlock() {
        super(0x80, UpdateFlag.CHAT);
    }

    @Override
    public void write(Player mob, ByteMessage msg) {
        msg.putShort(((mob.getChat().getColor() & 0xff) << 8) + (mob.getChat().getEffects() & 0xff), ByteOrder.LITTLE);
        msg.put(mob.getChat().getMessage().length, ByteTransform.F);
        msg.putBytesReverse(mob.getChat().getMessage());
    }

}
