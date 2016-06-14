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

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;

/**
 * F {@link Parser} implementation specifically designated for files that have a
 * series of {@code Object}s separated by a new line.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class NewLineParser extends Parser<Scanner, String> {

	/**
	 * An enumerated type representing the policies for when an empty line is
	 * encountered.
	 */
	public enum EmptyLinePolicy {

		/**
		 * Ignore that the line is empty and treat it as if it was a normal
		 * line, reading it and forwarding it to the implementing class.
		 */
		READ,

		/**
		 * Throw an {@link IllegalStateException} stating that the line was
		 * empty.
		 */
		EXCEPTION,

		/**
		 * Skip the line completely without any warning or thrown exception.
		 */
		SKIP
	}

	/**
	 * Creates a new {@link NewLineParser}.
	 *
	 * @param path
	 *            The path to the file being parsed.
	 */
	public NewLineParser(String path) {
		super(path);
	}

	@Override
	public String doRead(Scanner reader) throws Exception {
		return reader.nextLine();
	}

	@Override
	public Scanner getReader(BufferedReader in) throws Exception {
		return new Scanner(in);
	}

	@Override
	public boolean canRead(Scanner objectReader) throws Exception {
		return objectReader.hasNextLine();
	}

	@Override
	public void onReadComplete(List<String> readObjects) throws Exception {
		for (String nextLine : readObjects) {
			if (nextLine.isEmpty()) {
				EmptyLinePolicy linePolicy = requireNonNull(emptyLinePolicy(), "emptyLinePolicy == null");
				if (linePolicy == EmptyLinePolicy.SKIP) {
					continue;
				} else if (linePolicy == EmptyLinePolicy.EXCEPTION) {
					throw new IllegalStateException("nextLine.isEmpty()");
				}
			}
			readNextLine(nextLine);
		}
	}

	/**
	 * Reads the next line that was parsed by the {@link Scanner}.
	 *
	 * @param nextLine
	 *            The line to read.
	 */
	public abstract void readNextLine(String nextLine) throws Exception;

	/**
	 * @return The {@link EmptyLinePolicy} that determines what happens when an
	 *         empty line is encountered while parsing various lines of data.
	 */
	public EmptyLinePolicy emptyLinePolicy() {
		return EmptyLinePolicy.SKIP;
	}
}