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
package com.apache.net.session;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.apache.game.entity.player.Player;
import com.apache.net.NetworkConstants;
import com.apache.net.codec.IsaacCipher;
import com.apache.net.msg.GameMessage;
import com.apache.net.msg.InboundGameMessage;
import com.apache.net.msg.MessageRepository;
import com.apache.net.msg.OutboundGameMessage;
import com.apache.util.Utility;

import io.netty.channel.Channel;

/**
 * F {@link Session} implementation that handles networking for a {@link Player}
 * during gameplay.
 *
 * @author Juan Ortiz <http://github.org/TheReakJP>
 */
public final class GameSession extends Session {

    /**
     * The player assigned to this {@code GameSession}.
     */
    private final Player player;

    /**
     * The message encryptor.
     */
    private final IsaacCipher encryptor;

    /**
     * The message decryptor.
     */
    private final IsaacCipher decryptor;

    /**
     * The repository containing data for incoming messages.
     */
    private final MessageRepository messageRepository;

    /**
     * F bounded queue of inbound {@link GameMessage}s.
     */
    private final Queue<GameMessage> inboundQueue = new ArrayBlockingQueue<>(NetworkConstants.MESSAGE_LIMIT);

    /**
     * Creates a new {@link GameSession}.
     *
     * @param channel The channel for this session.
     * @param encryptor The message encryptor.
     * @param decryptor The message decryptor.
     * @param messageRepository The repository containing data for incoming
     * messages.
     */
    public GameSession(Player player, Channel channel, IsaacCipher encryptor, IsaacCipher decryptor,
            MessageRepository messageRepository) {
        super(channel);
        this.player = player;
        this.encryptor = encryptor;
        this.decryptor = decryptor;
        this.messageRepository = messageRepository;
    }

    @Override
    public void onDispose() {
        player.getWorld().queueLogout(player);
        inboundQueue.clear();
    }

    @Override
    public void handleUpstreamMessage(Object msg) {
        if (msg instanceof GameMessage) {
            inboundQueue.offer((GameMessage) msg);
        }
    }

    /**
     * Writes {@code msg} to the underlying channel; The channel is not flushed.
     *
     * @param msg The message to queue.
     */
    public void queue(OutboundGameMessage msg) {
        Channel channel = getChannel();

        if (channel.isActive()) {
            channel.writeAndFlush(msg.toGameMessage(player), channel.voidPromise());
        }
    }

    /**
     * Dequeues the inbound queue, handling all logic accordingly.
     */
    public void dequeue() {
        for (;;) {
            GameMessage msg = inboundQueue.poll();
            if (msg == null) {
                break;
            }
            try {
                InboundGameMessage inbound = messageRepository.getHandler(msg.getOpcode());
                inbound.handleInboundMessage(player, msg);
            } catch (Exception e) {
                Utility.log("-> " + e.getCause());
            }
        }
    }

    /**
     * @return The message encryptor.
     */
    public IsaacCipher getEncryptor() {
        return encryptor;
    }

    /**
     * @return The message decryptor.
     */
    public IsaacCipher getDecryptor() {
        return decryptor;
    }
}
