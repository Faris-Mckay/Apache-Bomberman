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

import com.apache.engine.task.InfiniteTask;
import java.util.logging.Logger;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class CleanupTask extends InfiniteTask {
    
    /**
     * A logger used to log messages.
     */
    public static final Logger logger = Logger.getLogger(CleanupTask.class.getName());

    public CleanupTask() {
        super(600);
    }
    
    @Override
    public void execute() {
        System.gc();
        System.runFinalization();
        logger.info("engine clean up has succesfully executed.");
    }
    
}
