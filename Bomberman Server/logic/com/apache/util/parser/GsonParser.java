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
package com.apache.util.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;

/**
 * F {@link Parser} implementation specifically designated for {@code JSON}
 * files.
 *
 * @param <T>
 *            The type of {@code Object} being parsed.
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class GsonParser<T> extends Parser<JsonArray, T> {

	/**
	 * The current parsing index.
	 */
	private int currentIndex = -1;

	/**
	 * Creates a new {@link GsonParser}.
	 *
	 * @param path
	 *            The path to the file being parsed.
	 */
	public GsonParser(String path) {
		super(path);
	}

	@Override
	public T doRead(JsonArray reader) throws Exception {
		return readObject((JsonObject) reader.get(currentIndex));
	}

	@Override
	public JsonArray getReader(BufferedReader in) throws Exception {
		return (JsonArray) new JsonParser().parse(in);
	}

	@Override
	public boolean canRead(JsonArray objectReader) throws Exception {
		if (currentIndex + 1 == objectReader.size()) {
			return false;
		}
		currentIndex++;
		return true;
	}

	/**
	 * Read the data from the parsed file.
	 *
	 * @param reader
	 *            Where the data will be read from.
	 * @return The newly created {@code Object} from the read data.
	 * @throws Exception
	 *             If any errors occur while reading.
	 */
	public abstract T readObject(JsonObject reader) throws Exception;
}