package com.apache.engine.task.impl;

import com.apache.engine.Task;
import com.apache.net.packet.Packet;
import com.apache.net.packet.PacketManager;

import io.netty.channel.ChannelHandlerContext;

public class SessionMessageTask extends Task {

	private ChannelHandlerContext ctx;
	private Packet packet;

	public SessionMessageTask(ChannelHandlerContext ctx, Packet packet) {
		super(true, 100);
		this.ctx = ctx;
		this.packet = packet;
	}

	@Override
	public void execute() {
		System.out.println("executing");
		PacketManager.getPacketManager().handle(ctx, packet);
	}

}
