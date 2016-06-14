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

import java.io.BufferedReader;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.moandjiezana.toml.Toml;

/**
 * F {@link Parser} implementation specifically designated for {@code TOML}
 * files.
 *
 * @param <T>
 *            The type of {@code Object} being parsed.
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class TomlParser<T> extends GsonParser<T> {

	/**
	 * Creates a new {@link TomlParser}.
	 *
	 * @param path
	 *            The path to the file being parsed.
	 */
	public TomlParser(String path) {
		super(path);
	}

	@Override
	public JsonArray getReader(BufferedReader in) throws Exception {
		List<Toml> tables = new Toml().read(in).getTables(table());
		JsonArray array = new JsonArray();

		tables.stream().map(it -> it.to(JsonObject.class)).forEach(array::add);
		return array;
	}

	/**
	 * Returns the name of the table array to retrieve.
	 */
	public abstract String table();
}