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
package com.apache.net.codec.login;

import com.apache.game.entity.player.Player;

/**
 * An immutable message that is written through a channel and forwarded to the
 * {@link LoginEncoder} where it is encoded and sent to the client.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class LoginResponseMessage {

	/**
	 * The actual login response.
	 */
	private final LoginResponse response;

	/**
	 * If the {@link Player} is flagged.
	 */
	private final boolean flagged;

	/**
	 * Creates a new {@link LoginResponseMessage}.
	 *
	 * @param response
	 *            The actual login response.
	 * 
	 * @param flagged
	 *            If the {@code Player} is flagged.
	 */
	public LoginResponseMessage(LoginResponse response, boolean flagged) {
		this.response = response;
		this.flagged = flagged;
	}

	/**
	 * Creates a new {@link LoginResponseMessage} with an authority level of
	 * {@code PLAYER} and a {@code flagged} value of {@code false}.
	 *
	 * @param response
	 *            The actual login response.
	 */
	public LoginResponseMessage(LoginResponse response) {
		this(response, false);
	}

	/**
	 * @return The actual login response.
	 */
	public LoginResponse getResponse() {
		return response;
	}

	/**
	 * @return {@code true} if flagged, {@code false} otherwise.
	 */
	public boolean isFlagged() {
		return flagged;
	}
}
