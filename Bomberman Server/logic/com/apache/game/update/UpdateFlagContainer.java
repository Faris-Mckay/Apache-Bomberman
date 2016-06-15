
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
 */package com.apache.game.update;

import java.util.EnumSet;

/**
 * F container backed by an {@link EnumSet} that manages all of the
 * {@link UpdateFlag}s for {@link Entity}s.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class UpdateFlagContainer {

	/**
	 * An enumerated type that holds all of the values representing update flags
	 * for {@link Entity}s.
	 */
	public enum UpdateFlag {
		CHAT, GRAPHIC, FACE_POSITION,
	}

	/**
	 * An {@link EnumSet} that will contain all active {@link UpdateFlag}s.
	 */
	private final EnumSet<UpdateFlag> flags = EnumSet.noneOf(UpdateFlag.class);

	/**
	 * Adds {@code flag} to the backing {@link EnumSet}.
	 *
	 * @param flag
	 *            The {@link UpdateFlag} to add.
	 */
	public void flag(UpdateFlag flag) {
		flags.add(flag);
	}

	/**
	 * Removes {@code flag} from the backing {@link EnumSet}.
	 *
	 * @param flag
	 *            The {@link UpdateFlag} to remove.
	 */
	public void unflag(UpdateFlag flag) {
		flags.remove(flag);
	}

	/**
	 * @return {@code true} if the backing {@link EnumSet} contains {@code
	 * flag}, false otherwise.
	 */
	public boolean get(UpdateFlag flag) {
		return flags.contains(flag);
	}

	/**
	 * @return {@code true} if the backing {@link EnumSet} is empty.
	 */
	public boolean isEmpty() {
		return flags.isEmpty();
	}

	/**
	 * Clears the backing {@link EnumSet} of all elements.
	 */
	public void clear() {
		flags.clear();
	}
}