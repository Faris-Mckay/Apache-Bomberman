/* 
 * This file is part of Bomberman.
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
package com.apache.event;

/**
 * A lightweight event (or task?). TODO Design review
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public class Event {
	/**
	 * Determines if this event should be intercepted, when given {@code args}.
	 * Always returns {@code true} if not overridden regardless of the
	 * arguments.
	 *
	 * @param args
	 *            The arguments for this event.
	 * @return {@code true} if this event can be intercepted, {@code false}
	 *         otherwise.
	 */
	public boolean matches(Object... args) {
		return true;
	}

}