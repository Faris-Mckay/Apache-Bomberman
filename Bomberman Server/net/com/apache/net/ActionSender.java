package com.apache.net;

import com.apache.game.entity.Player;

public class ActionSender {

    private Player player;

    public ActionSender(Player player) {
        this.player = player;
    }

    public ActionSender sendLobby() {

        return this;
    }
}