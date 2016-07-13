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
package com.apache;

import com.apache.util.Utility;

/**
 * * Instantiates a {@link Server} that will start this application.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class Main {

    /**
     * F private constructor to discourage external instantiation.
     */
    private Main() {
    }

    static {
        try {
            Thread.currentThread().setName("BombermanInitializationThread");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Invoked when this program is started, initializes the {@link Server}.
     *
     * @param args The runtime arguments, none of which are parsed.
     */
    public static void main(String[] args) {
        try {
            Server bomberman = new Server();
            bomberman.init();
        } catch (Exception e) {
            Utility.log(e.getMessage());
            System.exit(0);
        }
    }
}
