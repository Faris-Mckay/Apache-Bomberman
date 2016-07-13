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
package com.apache.util.parser;

import java.util.List;

import com.apache.net.msg.MessageRepository;
import com.apache.util.parser.MessageRepositoryParser.MessageRepositoryElement;
import com.google.gson.JsonObject;

/**
 * F {@link TomlParser} implementation that parses data that will later be
 * contained within a {@link MessageRepository}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class MessageRepositoryParser extends TomlParser<MessageRepositoryElement> {

    /**
     * The {@link MessageRepository} that the data will be added to.
     */
    private final MessageRepository messageRepository;

    /**
     * Creates a new {@link MessageRepositoryParser}.
     *
     * @param messageRepository The {@link MessageRepository} that the data will
     * be added to.
     */
    public MessageRepositoryParser(MessageRepository messageRepository) {
        super("./data/io/message_repository.toml");
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageRepositoryElement readObject(JsonObject reader) throws Exception {
        return new MessageRepositoryElement(reader.get("opcode").getAsInt(), reader.get("size").getAsInt(),
                reader.get("payload").getAsString());
    }

    @Override
    public void onReadComplete(List<MessageRepositoryElement> readObjects) throws Exception {
        for (MessageRepositoryElement it : readObjects) {
            messageRepository.addHandler(it.opcode, it.size, it.payload);
        }
    }

    @Override
    public String table() {
        return "message";
    }

    /**
     * F POJO representing a single read object.
     */
    protected final class MessageRepositoryElement {

        /**
         * The opcode.
         */
        private final int opcode;

        /**
         * The size.
         */
        private final int size;

        /**
         * The payload.
         */
        private final String payload;

        /**
         * Creates a new {@link MessageRepositoryElement}.
         *
         * @param opcode The opcode.
         * @param size The size.
         * @param payload The payload.
         */
        private MessageRepositoryElement(int opcode, int size, String payload) {
            this.opcode = opcode;
            this.size = size;
            this.payload = payload;
        }
    }
}
