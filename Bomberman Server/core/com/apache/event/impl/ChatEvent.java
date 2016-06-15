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
package com.apache.event.impl;

import com.apache.event.Event;

/**
 * An {@link Event} implementation sent whenever an {@link Player} talks.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class ChatEvent extends Event {

	/** The chat effects. */
	private final int effects;

	/** The chat color. */
	private final int color;

	/** The length of the message sent. */
	private final int textLength;

	/** The actual message sent. */
	private final byte[] text;

	/**
	 * Creates a new {@link ChatEvent}.
	 *
	 * @param effects
	 *            The chat effects.
	 * @param color
	 *            The chat color.
	 * @param textLength
	 *            The length of the message sent.
	 * @param text
	 *            The actual message sent.
	 */
	public ChatEvent(int effects, int color, int textLength, byte[] text) {
		this.effects = effects;
		this.color = color;
		this.textLength = textLength;
		this.text = text;
	}

	/**
	 * @return The chat effects.
	 */
	public int getEffects() {
		return effects;
	}

	/**
	 * @return The chat color.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return The length of the message sent.
	 */
	public int getTextLength() {
		return textLength;
	}

	/**
	 * @return The actual message sent.
	 */
	public byte[] getText() {
		return text;
	}
}
