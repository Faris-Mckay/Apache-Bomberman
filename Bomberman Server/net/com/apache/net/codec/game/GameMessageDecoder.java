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
package com.apache.net.codec.game;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Optional;

import com.apache.net.codec.ByteMessage;
import com.apache.net.codec.IsaacCipher;
import com.apache.net.codec.MessageType;
import com.apache.net.msg.GameMessage;
import com.apache.net.msg.MessageRepository;
import com.apache.util.Utility;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * F {@link ByteToMessageDecoder} implementation that decodes all
 * {@link ByteBuf}s into {@link GameMessage}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class GameMessageDecoder extends ByteToMessageDecoder {

	/**
	 * The ISAAC that will decrypt incoming messages.
	 */
	private final IsaacCipher decryptor;

	/**
	 * The repository containing data for incoming messages.
	 */
	private final MessageRepository messageRepository;

	/**
	 * The state of the message currently being decoded.
	 */
	private State state = State.OPCODE;

	/**
	 * The opcode of the message currently being decoded.
	 */
	private int opcode = -1;

	/**
	 * The size of the message currently being decoded.
	 */
	private int size = -1;

	/**
	 * The type of the message currently being decoded.
	 */
	private MessageType type = MessageType.RAW;

	/**
	 * The message that was decoded and needs to be queued.
	 */
	private Optional<GameMessage> currentMessage = Optional.empty();

	/**
	 * Creates a new {@link GameMessageDecoder}.
	 *
	 * @param decryptor
	 *            The decryptor for this decoder.
	 * @param messageRepository
	 *            The repository containing data for incoming messages.
	 */
	public GameMessageDecoder(IsaacCipher decryptor, MessageRepository messageRepository) {
		this.decryptor = decryptor;
		this.messageRepository = messageRepository;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		switch (state) {
		case OPCODE:
			opcode(in);
			break;
		case SIZE:
			size(in);
			break;
		case PAYLOAD:
			payload(in);
			break;
		}
		currentMessage.ifPresent($it -> {
			out.add($it);
			currentMessage = Optional.empty();
		});
	}

	/**
	 * Decodes the opcode of the {@link GameMessage}.
	 *
	 * @param in
	 *            The data being decoded.
	 */
	private void opcode(ByteBuf in) {
		if (in.isReadable()) {
			opcode = in.readUnsignedByte();
			opcode = (opcode - decryptor.nextInt()) & 0xFF;
			size = messageRepository.getSize(opcode);

			if (size == -1) {
				type = MessageType.VARIABLE;
			} else if (size == -2) {
				type = MessageType.VARIABLE_SHORT;
			} else {
				type = MessageType.FIXED;
			}

			if (size == 0) {
				queueMessage(Unpooled.EMPTY_BUFFER);
				return;
			}
			state = size == -1 || size == -2 ? State.SIZE : State.PAYLOAD;
		}
	}

	/**
	 * Decodes the size of the {@link GameMessage}.
	 *
	 * @param in
	 *            The data being decoded.
	 */
	private void size(ByteBuf in) {
		int bytes = size == -1 ? Byte.BYTES : Short.BYTES;

		if (in.isReadable(bytes)) {
			size = 0;

			for (int i = 0; i < bytes; i++) {
				size |= in.readUnsignedByte() << 8 * (bytes - 1 - i);
			}

			state = State.PAYLOAD;
		}
	}

	/**
	 * Decodes the payload of the {@link GameMessage}.
	 *
	 * @param in
	 *            The data being decoded.
	 */
	private void payload(ByteBuf in) {
		if (in.isReadable(size)) {
			queueMessage(in.readBytes(size));
		}
	}

	/**
	 * Prepares a {@link GameMessage} to be queued upstream and handled on the
	 * main game thread.
	 *
	 * @param payload
	 *            The payload of the {@code GameMessage}.
	 */
	private void queueMessage(ByteBuf payload) {
		checkState(opcode >= 0, "opcode < 0");
		checkState(size >= 0, "size < 0");
		checkState(type != MessageType.RAW, "type == MessageType.RAW");
		checkState(!currentMessage.isPresent(), "message already in queue");

		try {
			if (messageRepository.getHandler(opcode) == null) {
				Utility.log("No InboundGameMessage assigned to [opcode={" + opcode + "}]");
				currentMessage = Optional.empty();
				return;
			}

			currentMessage = Optional.of(new GameMessage(opcode, type, ByteMessage.wrap(payload)));
		} finally {
			resetState();
		}
	}

	/**
	 * Resets the state of this {@code MessageDecoder} to its default.
	 */
	private void resetState() {
		opcode = -1;
		size = -1;
		state = State.OPCODE;
	}

	/**
	 * An enumerated type whose elements represent all of the possible states of
	 * this {@code MessageDecoder}.
	 */
	private enum State {
		OPCODE, SIZE, PAYLOAD
	}
}