package com.apache.net;

import com.apache.game.entity.Player;

public class ActionSender {

    private Player player;

    public ActionSender(Player player) {
        this.setPlayer(player);
    }

    public ActionSender sendLobby() {

        return this;
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}