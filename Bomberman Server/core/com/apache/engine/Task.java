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
package com.apache.engine;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public abstract class Task {

	protected boolean stoppable, shouldStop;
	private int cycleRate;

	public Task(boolean stoppable, int cycleRate) {
		this.stoppable = stoppable;
		this.cycleRate = cycleRate;
	}

	public void execution() {
		execute();
	}

	public abstract void execute();

	/**
	 * @return the stoppable
	 */
	public boolean isStoppable() {
		return stoppable;
	}

	/**
	 * @return the shouldStop
	 */
	public boolean shouldStop() {
		return shouldStop;
	}

	/**
	 * @param shouldStop
	 *            the shouldStop to set
	 */
	public void setShouldStop(boolean shouldStop) {
		this.shouldStop = shouldStop;
	}

	/**
	 * @return the cycleRate
	 */
	public int getCycleRate() {
		return cycleRate;
	}

}
