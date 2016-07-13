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

import com.apache.util.Utility;

/**
 * F repository that contains data related to incoming {@link GameMessage}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class MessageRepository {

    /**
     * An array of integers that contain the incoming message sizes.
     */
    private final int[] sizes = new int[257];

    /**
     * An array of {@link InboundGameMessage}s that act as listeners for
     * incoming messages.
     */
    private final InboundGameMessage[] inboundHandlers = new InboundGameMessage[257];

    /**
     * Creates a new {@link MessageRepository}.
     */
    public MessageRepository() {
        Utility.ensureInitThread();
    }

    /**
     * Adds a new {@link InboundGameMessage} handler along with its size.
     *
     * @param opcode The opcode of the message handler.
     * @param size The size of the message.
     * @param inboundMessageName The class name of the
     * {@link InboundGameMessage}, implicitly prefixed with the {@code
     * com.apache.net.msg.in} package.
     * @throws ReflectiveOperationException If any errors occur while
     * instantiating the {@link InboundGameMessage}.
     */
    public void addHandler(int opcode, int size, String inboundMessageName) throws ReflectiveOperationException {
        Utility.ensureInitThread();

        Class<?> inboundMessageClass = Class.forName("com.apache.net.msg.in." + inboundMessageName);
        sizes[opcode] = size;
        inboundHandlers[opcode] = (InboundGameMessage) inboundMessageClass.newInstance();
    }

    /**
     * Retrieves the size of a message by {@code opcode}.
     *
     * @param opcode The opcode to retrieve the size of.
     * @return The size of {@code opcode}.
     */
    public int getSize(int opcode) {
        return sizes[opcode];
    }

    /**
     * Retrieves the incoming message handler for {@code opcode}.
     *
     * @param opcode The opcode to retrieve the message handler for.
     * @return The message handler for {@code opcode}, never {@code null}.
     */
    public InboundGameMessage getHandler(int opcode) {
        return inboundHandlers[opcode];
    }
}
