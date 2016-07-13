/* 
 * This file is part of Bomberman.
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
package com.apache.game.entity.attrib;

import java.util.Objects;

/**
 * A wrapper that contains simple functions to retrieve and modify the value
 * mapped with an {@link AttributeKey}.
 *
 * @param <T> The {@link Object} type represented by this value.
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class AttributeValue<T> {

    /**
     * The value within this wrapper.
     */
    private T value;

    /**
     * Creates a new {@link AttributeValue}.
     *
     * @param value The value within this wrapper.
     */
    public AttributeValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AttributeValue<?>) {
            AttributeValue<?> other = (AttributeValue<?>) obj;
            return Objects.equals(value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * @return The value within this wrapper.
     */
    public T get() {
        return value;
    }

    /**
     * Sets the value for {@link #value}.
     */
    public void set(T value) {
        this.value = value;
    }
}
