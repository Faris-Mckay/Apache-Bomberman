/* 
 * This file is property of Apache-GS.
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
package com.apache.task.impl;

import java.util.logging.Logger;

import com.apache.task.InfiniteTask;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class CleanupTask extends InfiniteTask {

    /**
     * F logger used to log messages.
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
