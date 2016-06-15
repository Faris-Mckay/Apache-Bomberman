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

import java.util.Optional;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public abstract class Task {

	protected boolean stoppable, shouldStop;
	private int cycleRate;

	/** An attachment for this {@code Task} instance. */
	private Optional<Object> key = Optional.empty();

	public Task(boolean stoppable, int cycleRate) {
		this.stoppable = stoppable;
		this.cycleRate = cycleRate;
	}

	/**
	 * Attaches {@code newKey} to this {@code Task}. The equivalent of doing
	 * {@code Optional.ofNullable(newKey)}.
	 *
	 * @param newKey
	 *            The new key to attach to this {@code Task}.
	 * @return An instance of this {@code Task} for method chaining.
	 */
	public Task attach(Object newKey) {
		key = Optional.ofNullable(newKey);
		return this;
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

	/** @return The attachment for this {@code Task} instance. */
	public Optional<Object> getAttachment() {
		return key;
	}

}
