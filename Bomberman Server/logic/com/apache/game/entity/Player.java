/* 
 * This file is part of Bomberman.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.game.entity;

import java.util.List;

import com.apache.net.ActionSender;

import io.netty.channel.Channel;

public class Player extends Entity {
	private int kills;

	/** The channel this player is connected to. */
	private Channel channel;

	private String username;
	private String password;
	private ActionSender actionSender = new ActionSender(this);

	public Player(Channel channel, String username, String password) {
		this.channel = channel;
		this.username = username;
		this.password = password;
	}

	public Player(String name) {
		this.username = name;
		setAlive(true);
	}

	public void updateOthers(List<Player> playersToUpdate) {

	}

	@Override
	public void updateSelf() {

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ActionSender getActionSender() {
		return actionSender;
	}

}
