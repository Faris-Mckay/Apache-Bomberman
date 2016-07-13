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

import static com.google.common.base.Preconditions.checkArgument;

import com.apache.net.codec.ByteMessage;
import com.apache.net.codec.MessageType;

/**
 * F message that can act as an inbound or outbound packet of data. It can be
 * safely used across multiple threads due to it being immutable.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class GameMessage {

    /**
     * The opcode of this message.
     */
    private final int opcode;

    /**
     * The size of this message.
     */
    private final int size;

    /**
     * The type of this message.
     */
    private final MessageType type;

    /**
     * The payload of this message.
     */
    private final ByteMessage payload;

    /**
     * Creates a new {@link GameMessage}.
     *
     * @param opcode The opcode of this message.
     * @param type The type of this message.
     * @param payload The payload of this message.
     */
    public GameMessage(int opcode, MessageType type, ByteMessage payload) {
        checkArgument(opcode >= 0, "opcode < 0");
        checkArgument(type != MessageType.RAW, "type == MessageType.RAW");

        this.opcode = opcode;
        this.type = type;
        this.payload = payload;
        size = payload.getBuffer().readableBytes();
    }

    /**
     * @return The opcode of this message.
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * @return The size of this message.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return The type of this message.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * @return The payload of this message.
     */
    public ByteMessage getPayload() {
        return payload;
    }
}
