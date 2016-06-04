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

import com.apache.engine.TaskEngine;
import com.apache.engine.task.impl.CleanupTask;
import java.util.logging.Logger;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Launcher {
    
    /**
     * The TaskEngine Server of the server
     */
    private TaskEngine engine;
    
    /**
     * A logger used to log messages.
     */
    public static final Logger logger = Logger.getLogger(CleanupTask.class.getName());
    
    /**
     * Main point of entry into the program
     * @param args input to the application
     */
    public static void main(String[] args){
        new Launcher().init();
    }
    
    /**
     * Initialise the server application
     */
    public void init(){
        logger.info("initializing game server...");
        engine = new TaskEngine();
        engine.submit(new CleanupTask());
        launch();
    }
    
    /**
     * Launch the server application
     */
    public void launch(){
        engine.startEngine();
    }
    
}
