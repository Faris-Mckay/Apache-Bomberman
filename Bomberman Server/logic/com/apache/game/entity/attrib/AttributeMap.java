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

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.common.collect.Iterators;

/**
 * A wrapper for a {@link Map} that contains a function to retrieve an
 * {@link AttributeValue} by its {@code String} key. The retrieval of attributes
 * is very high performing because it utilizes string interning and its own
 * method of caching.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class AttributeMap implements Iterable<Entry<String, AttributeValue<?>>> {

    /**
     * An {@link IdentityHashMap} that holds our {@link AttributeKey} and
     * {@link AttributeValue} pair.
     */
    private final Map<String, AttributeValue<?>> attributes = new IdentityHashMap<>(AttributeKey.ALIASES.size());

    /**
     * The last retrieved key.
     */
    private String lastKey;

    /**
     * The last retrieved value.
     */
    private AttributeValue lastValue;

    /**
     * Retrieves an {@link AttributeValue} by its {@code key}. Unfortunately
     * this function is not type safe, so it may throw a
     * {@link AttributeTypeException} if used with the wrong underlying type.
     *
     * @param key The key to retrieve the {@code AttributeValue} with.
     * @return The retrieved {@code AttributeValue}.
     */
    @SuppressWarnings("unchecked")
    public <T> AttributeValue<T> get(String key) {

        // noinspection StringEquality
        if (lastKey == requireNonNull(key)) {
            return lastValue;
        }

        AttributeKey<?> alias = Optional.ofNullable(AttributeKey.ALIASES.get(key))
                .orElse(AttributeKey.ALIASES.get(key.intern()));

        checkState(alias != null, "attributes need to be aliased in the AttributeKey class");

        try {
            lastKey = alias.getName();
            lastValue = attributes.computeIfAbsent(alias.getName(),
                    it -> new AttributeValue<>(alias.getInitialValue()));

            return lastValue;
        } catch (ClassCastException e) {
            throw new AttributeTypeException(alias);
        }
    }

    @Override
    public Iterator<Entry<String, AttributeValue<?>>> iterator() {
        return Iterators.unmodifiableIterator(attributes.entrySet().iterator());
    }
}
