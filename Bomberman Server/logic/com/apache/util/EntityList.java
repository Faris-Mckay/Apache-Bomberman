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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

import com.apache.game.entity.Entity;

/** @author Juan Ortiz <https://github.com/TheRealJP> */
public class EntityList<E extends Entity> implements Collection<E>, Iterable<E> {

	/**
	 * Internal entities array.
	 */
	private Entity[] entities;

	/**
	 * Current size.
	 */
	private int size = 0;// its weird ours works fine on matrix lol

	/**
	 * Creates an entity list with the specified capacity.
	 *
	 * @param capacity
	 *            The capacity.
	 */
	public EntityList(int capacity) {
		entities = new Entity[capacity + 1]; // do not use idx 0
	}

	/**
	 * Gets an entity.
	 *
	 * @param index
	 *            The index.
	 * @return The entity.
	 * @throws IndexOutOufBoundException
	 *             if the index is out of bounds.
	 */
	public Entity get(int index) {
		if (index <= 0 || index >= entities.length) {
			throw new IndexOutOfBoundsException();
		}
		return entities[index];
	}
	
    /**
     * Finds the first element that matches {@code filter}.
     *
     * @param filter The filter to apply to the elements of this sequence.
     * @return An {@link Optional} containing the element, or an empty {@code Optional} if no element was found.
     */
    public Optional<E> findFirst(Predicate<? super E> filter) {
        for (E e : this) {
            if (filter.test(e)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }
    

	/**
	 * Gets the index of an entity.
	 *
	 * @param entity
	 *            The entity.
	 * @return The index in the list.
	 */
	public int indexOf(Entity entity) {
		return entity.getId();
	}

	/**
	 * Gets the next free id.
	 *
	 * @return The next free id.
	 */
	private int getNextId() {
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] == null) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean add(E arg0) {
		int id = getNextId();
		if (id <= 0) {
			return false;
		}
		entities[id] = arg0;
		arg0.setId(id);
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		boolean changed = false;
		for (E entity : arg0) {
			if (add(entity)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public void clear() {
		for (int i = 1; i < entities.length; i++) {
			entities[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean contains(Object arg0) {
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] == arg0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		boolean failed = false;
		for (Object o : arg0) {
			if (!contains(o)) {
				failed = true;
			}
		}
		return !failed;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new EntityListIterator<E>(this);
	}

	@Override
	public boolean remove(Object arg0) {
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] == arg0) {
				entities[i] = null;
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean changed = false;
		for (Object o : arg0) {
			if (remove(o)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		boolean changed = false;
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] != null) {
				if (!arg0.contains(entities[i])) {
					entities[i] = null;
					size--;
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Entity[] toArray() {
		int size = size();
		Entity[] array = new Entity[size];
		int ptr = 0;
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] != null) {
				array[ptr++] = entities[i];
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] arg0) {
		Entity[] arr = toArray();
		return (T[]) Arrays.copyOf(arr, arr.length, arg0.getClass());
	}

}