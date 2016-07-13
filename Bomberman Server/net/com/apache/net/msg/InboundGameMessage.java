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

import com.apache.event.Event;
import com.apache.game.entity.player.Player;
import com.apache.net.codec.ByteMessage;
import com.apache.util.Utility;

/**
 * An inbound message handler that decodes all incoming {@link GameMessage}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class InboundGameMessage {

    /**
     * Read the {@code msg} and return the {@link Event} that will be forwarded
     * to the {@link manager}, if any. This is only used for the decoding,
     * validation, and basic logic stages of an incoming message. All event type
     * logic should be handled within plugins.
     *
     * @param player The player.
     * @param msg The message to read.
     * @return The {@code Event} that will be forwarded to a plugin,
     * {@code null} if no {@code Event} should be forwarded.
     * @throws Exception If any exceptions are thrown. Will later be caught by
     * the session logger.
     */
    public abstract Event readMessage(Player player, GameMessage msg) throws Exception;

    /**
     * Reads the payload from the inbound {@code msg}, and notifies all
     * listeners of any events constructed from the operation. Return the buffer
     * used to hold the payload back to it's buffer pool, if applicable.
     *
     * @param player The player.
     * @param msg The message to read.
     */
    public final void handleInboundMessage(Player player, GameMessage msg) {
        try {
            Event event = readMessage(player, msg);
            // event logic tbw
        } catch (Exception e) {
            Utility.log(e.getMessage());
            player.logout();
        } finally {
            ByteMessage payload = msg.getPayload();

            if (payload.refCnt() > 0) {
                payload.release();
            }
        }
    }
}
