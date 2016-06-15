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
package com.apache.net;

import java.io.File;
import java.math.BigInteger;

import com.apache.net.session.Session;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.moandjiezana.toml.Toml;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.ResourceLeakDetector.Level;

/**
 * F utility class that only contains Netty constants.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class NetworkConstants {

	static {
		try {
			JsonObject reader = new Toml().read(new File("./data/bomberman.toml")).getTable("settings")
					.to(JsonObject.class);
			PORT = reader.get("port").getAsInt();
			RSA_MODULUS = new BigInteger(reader.get("rsa_modulus").getAsString());
			RSA_EXPONENT = new BigInteger(reader.get("rsa_exponent").getAsString());
			RESOURCE_LEAK_DETECTION = Level.valueOf(reader.get("resource_leak_detection_level").getAsString());
			CONNECTION_LIMIT = reader.get("connection_threshold").getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * The resource leak detection level, should be {@code PARANOID} in a
	 * development environment and {@code DISABLED} in a production environment.
	 */
	public static final Level RESOURCE_LEAK_DETECTION;

	/**
	 * The port that the server will be bound on.
	 */
	public static final int PORT;

	/**
	 * The public RSA exponent value.
	 */
	public static final BigInteger RSA_MODULUS;

	/**
	 * The private RSA exponent value.
	 */
	public static final BigInteger RSA_EXPONENT;

	/**
	 * The maximum amount of connections allowed per channel.
	 */
	public static final int CONNECTION_LIMIT;

	/**
	 * The amount of {@code SECONDS} that must elapse for a channel to be
	 * disconnected after no read operations.
	 */
	public static final int READ_IDLE_SECONDS = 5;

	/**
	 * The maximum amount of incoming messages per cycle.
	 */
	public static final int MESSAGE_LIMIT = 15;

	/**
	 * The preferred ports for the user to use, a log message will be printed if
	 * none of these ports are used.
	 */
	public static final ImmutableSet<Integer> PREFERRED_PORTS = ImmutableSet.of(43594, 5555);

	/**
	 * F list of exceptions that are ignored when received from Netty.
	 */
	public static final ImmutableList<String> IGNORED_EXCEPTIONS = ImmutableList.of(
			"An existing connection was forcibly closed by the remote host",
			"An established connection was aborted by the software in your host machine");

	/**
	 * An {@link AttributeKey} that is used to retrieve the session instance
	 * from the attribute map of a {@link Channel}.
	 */
	public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session.KEY");

	/**
	 * F private constructor to discourage external instantiation.
	 */
	private NetworkConstants() {
	}
}