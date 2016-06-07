package com.apache.game.update;

import java.util.BitSet;

public class UpdateFlags {

	/** A bit set holding the values for the update flags. */
	private BitSet bits = new BitSet(Flag.values().length);

	/** Holds all of the update flag constants. */
	public enum Flag {
		APPEARANCE, CHAT, GRAPHICS, ANIMATION, FACE_ENTITY, FACE_COORDINATE,
	}

	/**
	 * Flags the argued update flag. This method will do nothing if the argued
	 * flag already has a value of <code>true</code>.
	 * 
	 * @param flag
	 *            the update flag that will be flagged.
	 */
	public void flag(Flag flag) {
		bits.set(flag.ordinal());
	}

	/**
	 * Flips the value of the argued update flag.
	 * 
	 * @param flag
	 *            the update flag that will be flipped.
	 */
	public void flip(Flag flag) {
		bits.flip(flag.ordinal());
	}

	/**
	 * Gets the value of an update flag.
	 * 
	 * @param flag
	 *            the update flag to get the value of.
	 * @return the value of the flag.
	 */
	public boolean get(Flag flag) {
		return bits.get(flag.ordinal());
	}

	/**
	 * Gets if an update is required. This is determined by if the backing
	 * {@link BitSet} is empty or not.
	 * 
	 * @return true if an update is required, meaning the backing bit set is not
	 *         empty.
	 */
	public boolean isUpdateRequired() {
		return !bits.isEmpty();
	}

	/**
	 * Resets the update flags.
	 */
	public void reset() {
		bits.clear();
	}
}
