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
package com.apache.engine.task.impl;

import com.apache.game.entity.Player;
import com.apache.engine.task.FiniteTask;
import com.apache.Constants;
import com.apache.game.Game;
import java.util.List;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class GameTask extends FiniteTask {
    
    private Game game;

    public GameTask(Game game) {
        super(Constants.PLAYER_PROCESS_PER_CYCLE);
        this.game = game;
    }

    @Override
    public void execute() {
        if(!game.isGameActive()){
            setShouldStop(true);
        }
        for(Player player : game.getPlayers()){
            player.updateOthers(game.getPlayers());
        }
    }
    
}
