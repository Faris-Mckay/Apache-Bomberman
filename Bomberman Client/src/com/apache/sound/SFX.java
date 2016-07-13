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
package com.apache.sound;

import com.apache.Settings;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * the handling of SFX through game client, this is done through the jl library
 * and loads pre-defined SFX from the data folder to be played upon request
 *
 * @author Faris McKay <https://github.com/faris-mckay>
 */
public class SFX {

    /**
     * Holds the instance for SFX Player
     */
    private static final SFX INSTANCE = new SFX();

    private SFX() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    /**
     * Singleton pattern
     *
     * @return the instance of this class
     */
    public static SFX getInstance() {
        return INSTANCE;
    }

    /**
     * Used to handle music playback should stop or start
     */
    public static boolean musicPlaying;

    /**
     * Caches the musicPlayer
     */
    private Player musicPlayer;

    /**
     * Locates, stores and plays the sound effect of an action when called
     */
    public void playSound(String soundName) {
        if (!Settings.SOUND_ENABLED) {
            return;
        }
        musicPlaying = true;
        try {
            FileInputStream fis = new FileInputStream("./data/sfx/" + soundName + ".mp3");
            BufferedInputStream bis = new BufferedInputStream(fis);
            musicPlayer = new Player(bis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    musicPlayer.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }

    /**
     * Closes the connection between file and server and ends music playback
     *
     * @throws javazoom.jl.decoder.JavaLayerException
     */
    public void close() throws JavaLayerException {
        if (musicPlayer != null) {
            musicPlayer.close();
            musicPlayer = null;
            musicPlaying = false;
        }
    }

}
