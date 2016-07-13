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
package com.apache.game.entity.player;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import com.apache.game.chat.Chat;
import com.apache.game.entity.Entity;
import com.apache.game.entity.EntityType;
import com.apache.game.update.UpdateFlagContainer.UpdateFlag;
import com.apache.net.BombermanContext;
import com.apache.net.codec.ByteMessage;
import com.apache.net.msg.OutboundGameMessage;
import com.apache.net.msg.out.SendLogoutMessage;
import com.apache.net.session.GameSession;
import com.apache.net.session.Session;

import io.netty.channel.Channel;

public class Player extends Entity {

    private int kills;

    /**
     * The channel this player is connected to.
     */
    private Channel channel;

    /**
     * The credentials of this {@code Player}.
     */
    private final PlayerCredentials credentials;

    /**
     * The {@link Chat} message to send during this update block.
     */
    private Chat chat;

    /**
     * The {@link Session} assigned to this {@code Player}.
     */
    private GameSession session;

    /**
     * The current cached block for this update cycle.
     */
    private ByteMessage cachedBlock;

    /**
     * Creates a new {@link Player}.
     *
     * @param context The context to be managed in.
     */
    public Player(BombermanContext context, PlayerCredentials credentials) {
        super(context);
        this.credentials = credentials;
    }

    /**
     * Send {@code chat} message for this cycle.
     *
     * @param chat The {@link Chat} message to send during this update block.
     */
    public void chat(Chat chat) {
        this.chat = requireNonNull(chat);
        updateFlags.flag(UpdateFlag.CHAT);
    }

    /**
     * F shortcut function to {@link GameSession#queue(OutboundGameMessage)}.
     */
    public void queue(OutboundGameMessage msg) {
        session.queue(msg);
    }

    /**
     * Gracefully logs this {@code Player} instance out of the server.
     */
    public void logout() {
        Channel channel = session.getChannel();
        if (channel.isActive()) {
            queue(new SendLogoutMessage());
        }
    }

    /**
     * @return the kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * add one to the kills
     */
    public void addKill() {
        this.kills += 1;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public EntityType type() {
        return EntityType.PLAYER;
    }

    /**
     * @return The username of this {@code Player}.
     */
    public String getUsername() {
        return credentials.getUsername();
    }

    /**
     * @return The password of this {@code Player}.
     */
    public String getPassword() {
        return credentials.getPassword();
    }

    /**
     * @return The username hash of this {@code Player}.
     */
    public long getUsernameHash() {
        return credentials.getUsernameHash();
    }

    /**
     * @return The {@link Session} assigned to this {@code Player}.
     */
    public GameSession getSession() {
        return session;
    }

    /**
     * Sets the value for {@link #session}.
     */
    public void setSession(GameSession session) {
        checkState(this.session == null, "session already set!");
        this.session = session;
    }

    /**
     * @return The {@link Chat} message to send during this update block.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * @return The current cached block for this update cycle.
     */
    public ByteMessage getCachedBlock() {
        return cachedBlock;
    }

    /**
     * Sets the value for {@link #cachedBlock}.
     */
    public void setCachedBlock(ByteMessage cachedBlock) {
        ByteMessage currentBlock = this.cachedBlock;

        // Release reference to currently cached block, if we have one.
        if (currentBlock != null) {
            currentBlock.release();
        }

        // If we need to set a new cached block, retain a reference to it.
        // Otherwise it means that the current block
        // reference was just being cleared.
        if (cachedBlock != null) {
            cachedBlock.retain();
        }
        this.cachedBlock = cachedBlock;
    }
}
