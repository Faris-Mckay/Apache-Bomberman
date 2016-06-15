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
package com.apache.game.update;

import com.apache.game.entity.player.Player;
import com.apache.game.update.UpdateFlagContainer.UpdateFlag;

/**
 * An {@link UpdateBlock} implementation specific to {@link Player}s contained
 * within an {@link UpdateBlockSet} and sent within a
 * {@link PlayerUpdateMessageWriter}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class PlayerUpdateBlock extends UpdateBlock<Player> {

    /**
     * Creates a new {@link PlayerUpdateBlock}.
     *
     * @param mask The bit mask for this update block.
     * @param flag The update flag associated with this update block.
     */
    public PlayerUpdateBlock(int mask, UpdateFlag flag) {
        super(mask, flag);
    }
}