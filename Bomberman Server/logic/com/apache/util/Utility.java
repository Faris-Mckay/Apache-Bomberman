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
package com.apache.util;

import static com.google.common.base.Preconditions.checkState;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.apache.map.Location;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class Utility {

    /**
     * Returns the delta coordinates. Note that the returned Location is not an
     * actual position, instead it's values represent the delta values between
     * the two arguments.
     *
     * @param a the first position.
     * @param b the second position.
     * @return the delta coordinates contained within a position.
     */
    public static Location delta(Location a, Location b) {
        return new Location(b.getX() - a.getX(), b.getY() - a.getY());
    }

    /**
     * Logs a message for output.
     */
    public static void log(String msg) {
        Logger.getLogger(msg.getClass().getName()).info(msg);
    }

    /**
     * Logs a message for output with an associated level of severity.
     */
    public static void log(String msg, Level level) {
        Logger logger = Logger.getLogger(msg.getClass().getName());
        logger.log(level, msg);
    }

    /**
     * Throws an {@link IllegalStateException} if the current thread is not an
     * initialization thread.
     */
    public static void ensureInitThread() {
        String threadName = Thread.currentThread().getName();
        checkState(threadName.equals("BombermanInitializationThread"), "can only be done during initialization");
    }

    /**
     * A general purpose {@link Gson} instance that has no registered type
     * adapters.
     */
    public static final Gson GSON = new GsonBuilder().disableInnerClassSerialization().setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    /**
     * Gets {@code element} as {@code clazz} type.
     *
     * @param element The element to get as {@code clazz}.
     * @param clazz The new type of {@code element}.
     * @param <T> The underlying type.
     * @return The {@code element} as {@code T}.
     */
    public static <T> T getAsType(JsonElement element, Class<T> clazz) {
        return GSON.fromJson(element, clazz);
    }

}
