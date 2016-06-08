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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.apache.game.entity.Player;
import com.apache.util.BlockingExecutorService;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Game {
	/** Thread for loading all game components. */
	private BlockingExecutorService backgroundLoader = new BlockingExecutorService(Executors.newSingleThreadExecutor());

	/** The engine thread used to create this game. */
	// private GameEngine engine;

	private int gameID;

	private boolean gameActive;

	/**
	 * List capacity set to 8. TODO: We should end the game when the list has
	 * reached 1 player. <br>
	 * Example: <br>
	 * <code>void gameloop(){ <br>
	 * while(!players.size <= 1){ <br>
	 * doGameLogic();<br>
	 * } declareWinner();<br>endGame();</code>
	 */
	private volatile List<Player> players = Collections.synchronizedList(new ArrayList<Player>(8));

	public Game() {
		backgroundLoader.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				// load npcs
				System.out.println("Loading NPCs?");
				return null;
			}
		});
	}

	public Game(Player player) {
		players.add(player);
		gameActive = true;
	}

	public BlockingExecutorService getBackgroundLoader() {
		return backgroundLoader;
	}

	/** Initializes the game. */
	public void init(GameEngine engine) throws Exception {
		// this.engine = engine;
		this.registerGameEvents();
	}

	/** Registers all the recurrent tasks (for this game only). */
	private void registerGameEvents() {
		// submit(new EntityUpdateEvent());
	}

	/**
	 * TODO: Declare winner and remove all pointers to minimize memory leaks
	 * (just incase garbage collection misses something).
	 */
	private void endGame() {
		// declare winner.
		players.clear();
		gameID = 0;
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
	 * @param gameActive
	 *            the gameActive to set
	 */
	public void setGameActive(boolean gameActive) {
		this.gameActive = gameActive;
	}

	/** Return this game's id. */
	public int getID() {
		return gameID;
	}
}
