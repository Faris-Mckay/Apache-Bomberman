package com.apache.engine.task.impl;

import com.apache.engine.Task;
import com.apache.game.entity.Player;
import com.apache.net.packet.PacketBuilder;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class SessionLoginTask extends Task {

	private Player player;

	public SessionLoginTask(Player player) {
		super(true, 60);
		this.player = player;
	}

	@Override
	public void execute() {
		int returnCode = 1;
		PacketBuilder pb = new PacketBuilder(returnCode);

		player.getChannel().write(pb).addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				player.getActionSender().sendLobby();
			}
		});
	}

}