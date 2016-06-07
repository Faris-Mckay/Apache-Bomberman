/* 
 * This file is property of Apache-GS.
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
package com.apache.game;

import com.apache.game.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Game {
    
    private List<Player> players = new ArrayList();
    private boolean gameActive;
    
    public Game(Player player){
        players.add(player);
        gameActive = true;
    }
    
    public void addPlayer(Player player){
        if(getPlayers().size() > 9){
            return;
        }
        getPlayers().add(player);
    }
    
    public void removePlayer(Player player){
        getPlayers().remove(player);
        if(getPlayers().size() < 1){
            endGame();
        }
    }
    
    private void endGame(){
        setGameActive(false);
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the gameActive
     */
    public boolean isGameActive() {
        return gameActive;
    }

    /**
     * @param gameActive the gameActive to set
     */
    public void setGameActive(boolean gameActive) {
        this.gameActive = gameActive;
    }
    
}
