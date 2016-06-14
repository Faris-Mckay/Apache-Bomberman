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
package com.apache.net;

import com.apache.Server;
import com.apache.game.GameService;
import com.apache.game.World;

/**
 * An object assigned to every {@link Server} instance. It represents a single
 * instance of the Apache-Bomberman universe in it's entirety, that being a
 * {@link World}, and the {@link GameService} that runs the aforementioned
 * things.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class BombermanContext {

	/**
	 * The {@link World} that manages various entities.
	 */
	private final World world = new World(this);

	/**
	 * The {@link GameService} that manages game logic processing.
	 */
	private final GameService service = new GameService(this);

	/**
	 * F package-private constructor to discourage external instantiation
	 * outside of the {@code com.apache} package.
	 */
	public BombermanContext() {
	}

	/**
	 * @return The {@link GameService} in this context.
	 */
	public GameService getService() {
		return service;
	}

	/**
	 * @return The {@link World} in this context.
	 */
	public World getWorld() {
		return world;
	}

}