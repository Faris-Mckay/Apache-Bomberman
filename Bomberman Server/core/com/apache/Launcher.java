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
package com.apache;

import java.util.logging.Logger;

import com.apache.engine.TaskEngine;
import com.apache.engine.task.impl.CleanupTask;
import com.apache.game.GameEngine;
import com.apache.game.Lobby;
import com.apache.net.Network;
import com.apache.util.Constants;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Launcher {

	/**
	 * The TaskEngine Server of the server
	 */
	private TaskEngine taskEngine;

	private Network network;
	private GameEngine gameEngine;

	/**
	 * A logger used to log messages.
	 */
	public static final Logger logger = Logger.getLogger(CleanupTask.class.getName());

	/**
	 * Main point of entry into the program
	 * 
	 * @param args
	 *            input to the application
	 */
	public static void main(String[] args) {
		new Launcher().init();
	}

	/**
	 * Initialise the server application
	 */
	public void init() {
		try {
			logger.info("Initializing game server...");
			taskEngine = new TaskEngine();
			taskEngine.submit(new CleanupTask());
			network = new Network(Constants.PORT_ID);
			gameEngine = new GameEngine();
			gameEngine.start();

			Lobby.getLobby().setPort(43594);
			Lobby.getLobby().setLobbyId(1);
			Lobby.getLobby().init(gameEngine);
			if (Lobby.getLobby().getBackgroundLoader().getPendingTaskAmount() > 0) {
				System.out.println("Waiting for pending background loading tasks...");
				Lobby.getLobby().getBackgroundLoader().waitForPendingTasks();
			}
			Lobby.getLobby().getBackgroundLoader().shutdown();

			network.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch the server application
	 */
	public void launch() {
		taskEngine.startEngine();
	}

}
