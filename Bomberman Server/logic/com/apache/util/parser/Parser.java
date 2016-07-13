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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.apache.util.Utility;

/**
 * The base class for all parsers. Provides functionality that allows subclasses
 * to perform asynchronous parsing of various types of files. Also provides a
 * completion handler with a {@link List} of the successfully parsed
 * {@code Objects}.
 *
 * @param <T1> The reader that will be parsing the file.
 * @param <T2> The {@code Object} being parsed.
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public abstract class Parser<T1, T2> implements Runnable {

    /**
     * The path to the file being parsed.
     */
    private final Path path;

    /**
     * Creates a new {@link Parser}.
     *
     * @param path The path to the file being parsed.
     */
    public Parser(String path) {
        this.path = Paths.get(path);
    }

    @Override
    public final void run() {
        try (BufferedReader in = Files.newBufferedReader(path)) {
            List<T2> readObjects = new ArrayList<>();
            T1 reader = getReader(in);

            while (canRead(reader)) {
                readObjects.add(doRead(reader));
            }
            onReadComplete(readObjects);
        } catch (Exception e) {
            Utility.log(e.getMessage());
        }
    }

    /**
     * Read the contents of the file with {@code reader}.
     *
     * @param reader The reader to parse the file with.
     * @return The {@code Object} that was read from the {@code reader}.
     * @throws Exception If any errors occur while reading.
     */
    public abstract T2 doRead(T1 reader) throws Exception;

    /**
     * Gets the reader of the file through {@code in}.
     *
     * @param in The {@link BufferedReader} used to get the reader of the file.
     * @return The reader of the file.
     * @throws Exception If any errors occur while getting the reader.
     */
    public abstract T1 getReader(BufferedReader in) throws Exception;

    /**
     * Determines if the {@code objectReader} can read another {@code Object}.
     *
     * @param objectReader The reader for the {@code Object}s being parsed.
     * @return {@code true} if more data can be read, {@code false} otherwise.
     * @throws Exception If any errors occur while determining the read status.
     */
    public abstract boolean canRead(T1 objectReader) throws Exception;

    /**
     * Invoked when this parser finishes parsing all {@code Object}s.
     *
     * @param readObjects The list of {@code Object}s that were parsed, possibly
     * at a size of {@code 0}.
     * @throws Exception If any errors occur while executing completion logic.
     */
    public abstract void onReadComplete(List<T2> readObjects) throws Exception;
}
