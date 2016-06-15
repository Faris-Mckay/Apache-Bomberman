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

/**
 * An {@link RuntimeException} implementation thrown when there is a type
 * mismatch with attributes, resulting in a {@link ClassCastException}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class AttributeTypeException extends RuntimeException {

	/**
	 * Creates a new {@link AttributeTypeException}.
	 *
	 * @param alias
	 *            The {@link AttributeKey} alias which the exception is being
	 *            thrown for.
	 */
	public AttributeTypeException(AttributeKey<?> alias) {
		super("invalid attribute{" + alias.getName() + "} type! expected{" + alias.getTypeName() + "}");
	}
}