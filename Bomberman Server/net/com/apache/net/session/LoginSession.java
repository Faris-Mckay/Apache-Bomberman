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

import static com.google.common.base.Preconditions.checkState;

import com.apache.game.World;
import com.apache.game.entity.player.Player;
import com.apache.game.entity.player.PlayerCredentials;
import com.apache.game.entity.player.PlayerSerializer;
import com.apache.net.BombermanContext;
import com.apache.net.NetworkConstants;
import com.apache.net.codec.game.GameMessageDecoder;
import com.apache.net.codec.game.GameMessageEncoder;
import com.apache.net.codec.login.LoginCredentialsMessage;
import com.apache.net.codec.login.LoginResponse;
import com.apache.net.codec.login.LoginResponseMessage;
import com.apache.net.msg.MessageRepository;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPipeline;

/**
 * F {@link Session} implementation that handles networking for a {@link Player}
 * during login.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class LoginSession extends Session {

    /**
     * The {@link World} dedicated to this {@code LoginSession}.
     */
    private final BombermanContext context;

    /**
     * The repository containing data for incoming messages.
     */
    private final MessageRepository messageRepository;

    /**
     * Creates a new {@link GameSession}.
     *
     * @param context The context to be managed under.
     * @param channel The {@link Channel} for this session.
     * @param messageRepository The repository containing data for incoming
     * messages.
     */
    public LoginSession(BombermanContext context, Channel channel, MessageRepository messageRepository) {
        super(channel);
        this.context = context;
        this.messageRepository = messageRepository;
    }

    @Override
    public void handleUpstreamMessage(Object msg) throws Exception {
        if (msg instanceof LoginCredentialsMessage) {
            LoginCredentialsMessage credentials = (LoginCredentialsMessage) msg;
            handleCredentials(credentials);
        }
    }

    /**
     * Loads the character file and sends the {@link LoginResponse} code to the
     * client.
     *
     * @param msg The message containing the credentials.
     * @throws Exception If any errors occur while handling credentials.
     */
    private void handleCredentials(LoginCredentialsMessage msg) throws Exception {
        Channel channel = getChannel();
        World world = context.getWorld();
        LoginResponse response = LoginResponse.NORMAL;
        ChannelPipeline pipeline = msg.getPipeline();

        String username = msg.getUsername();
        String password = msg.getPassword();

        checkState(username.matches("^[a-zA-Z0-9_ ]{1,12}$") && !password.isEmpty() && password.length() <= 20);

        Player player = new Player(context, new PlayerCredentials(username, password));

        if (world.getPlayer(player.getUsernameHash()).isPresent()) {
            response = LoginResponse.ACCOUNT_ONLINE;
        }

        if (response == LoginResponse.NORMAL) {
            PlayerSerializer deserializer = new PlayerSerializer(player);
            response = deserializer.load(password);
        }

        ChannelFuture future = channel.writeAndFlush(new LoginResponseMessage(response));
        if (response != LoginResponse.NORMAL) {
            future.addListener(ChannelFutureListener.CLOSE);
        } else {
            future.addListener(it -> {
                pipeline.replace("login-encoder", "game-encoder", new GameMessageEncoder(msg.getEncryptor()));
                pipeline
                        .replace("login-decoder", "game-decoder", new GameMessageDecoder(msg.getDecryptor(), messageRepository));

                GameSession session = new GameSession(player, channel, msg.getEncryptor(), msg.getDecryptor(),
                        messageRepository);

                channel.attr(NetworkConstants.SESSION_KEY).set(session);
                player.setSession(session);

                world.queueLogin(player);
            });
        }
    }
}
