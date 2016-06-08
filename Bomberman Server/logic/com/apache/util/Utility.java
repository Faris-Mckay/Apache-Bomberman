package com.apache.util;

import java.util.logging.Logger;

import com.apache.map.Location;

public class Utility {
	/**
	 * Returns the delta coordinates. Note that the returned Location is not an
	 * actual position, instead it's values represent the delta values between
	 * the two arguments.
	 * 
	 * @param a
	 *            the first position.
	 * @param b
	 *            the second position.
	 * @return the delta coordinates contained within a position.
	 */
	public static Location delta(Location a, Location b) {
		return new Location(b.getX() - a.getX(), b.getY() - a.getY());
	}

	/** Logs a message for output. */
	public static void log(String msg) {
		Logger.getLogger(msg.getClass().getName()).info(msg);
	}

}
