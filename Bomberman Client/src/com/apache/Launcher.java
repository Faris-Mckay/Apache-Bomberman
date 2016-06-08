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

import com.apache.net.Network;
import com.apache.sound.SFX;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Launcher {

	private static void connectToServer() {
		Network network = new Network(Settings.HOST, Settings.PORT_ID);
		network.init();
	}

	/**
	 * Main point of entry into the program
	 *
	 * @param args
	 *            input to the application
	 */
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath",
				new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName())
						.getAbsolutePath());
		SFX.getInstance().playSound("screenshot");
		try {
			AppGameContainer container = new AppGameContainer(new Game(Settings.GAME_TITLE));
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(Settings.TARGET_FRAME_RATE);
			container.setSoundOn(true);
			if (!Settings.DEBUG)
				container.setShowFPS(false);
                        Thread networkThread = new Thread(){
                            @Override
                            public void run(){
                                connectToServer();
                            }
                        };
                        networkThread.start();
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
