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
package com.apache.task;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public abstract class FiniteTask extends Task {   
    
    public FiniteTask(int cycleRate){
        super(true, cycleRate);
    }
    
}