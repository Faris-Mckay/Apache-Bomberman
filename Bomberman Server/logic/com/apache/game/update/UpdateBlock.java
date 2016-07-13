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

import java.util.Objects;

import com.apache.game.entity.Entity;
import com.apache.game.entity.player.Player;
import com.apache.game.update.UpdateFlagContainer.UpdateFlag;
import com.apache.net.codec.ByteMessage;
import com.google.common.base.MoreObjects;

/**
 * F single update block contained within an {@link UpdateBlockSet} and sent
 * within a {@link Player} or {@link Npc} update message.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class UpdateBlock<E extends Entity> {

    /**
     * The bit mask for this update block.
     */
    private final int mask;

    /**
     * The update flag associated with this update block.
     */
    private final UpdateFlag flag;

    /**
     * Creates a new {@link UpdateBlock}.
     *
     * @param mask The bit mask for this update block.
     * @param flag The update flag associated with this update block.
     */
    public UpdateBlock(int mask, UpdateFlagContainer.UpdateFlag flag) {
        this.mask = mask;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", flag.name()).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UpdateBlock) {
            UpdateBlock<?> other = (UpdateBlock<?>) obj;
            return flag == other.flag;
        }
        return false;
    }

    /**
     * Writes the data for this update block into {@code buf}.
     *
     * @param mob The {@link MobileEntity} this update block is being written
     * for.
     * @param msg The buffer to write the data to.
     */
    public abstract void write(E mob, ByteMessage msg);

    /**
     * @return The bit mask for this update block.
     */
    public int getMask() {
        return mask;
    }

    /**
     * @return The update flag associated with this update block.
     */
    public UpdateFlag getFlag() {
        return flag;
    }
}
