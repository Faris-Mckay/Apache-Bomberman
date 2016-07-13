/* 
 * This file is property of Apache-GS.
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
package com.apache.game;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.apache.game.entity.Entity;
import com.apache.game.entity.player.Player;
import com.apache.net.BombermanContext;
import com.apache.task.Task;
import com.apache.task.TaskEngine;
import com.apache.task.impl.CleanupTask;
import com.apache.util.EntityList;
import com.apache.util.StringUtility;

public class World {

    /**
     * The total amount of {@link Player}s that can be either logged in per game
     * loop.
     */
    public static final int LOGIN_THRESHOLD = 50;

    /**
     * An instance of the {@link BombermanContext}.
     */
    private final BombermanContext context;

    private GameEngine engine;

    private int lobbyId, port;

    /**
     * The {@link TaskManager} that manages cycle based tasks.
     */
    private final TaskEngine tasks = new TaskEngine();

    /**
     * F {@link Queue} of {@link Player}s awaiting login.
     */
    private final Queue<Player> logins = new ConcurrentLinkedQueue<>();

    /**
     * F {@link Queue} of {@link Player}s awaiting logout.
     */
    private final Queue<Player> logouts = new ConcurrentLinkedQueue<>();

    /**
     * The list of {@link Player}s in the lobby.
     */
    private EntityList<Player> players = new EntityList<Player>(2000);

    /**
     * The {@link WorldSynchronizer} that will perform updating for all
     * {@link MobileEntity}s.
     */
    private final WorldSynchronizer synchronizer = new WorldSynchronizer(this);

    /**
     * Creates a new {@link World}.
     *
     * @param context An instance of the {@link BombermanContext}.
     */
    public World(BombermanContext context) {
        this.context = context;
    }

    /**
     * Schedules a {@link Task} using the underlying {@link TaskEngine}.
     *
     * @param t The {@code Task} to schedule.
     */
    public void schedule(Task t) {
        tasks.submit(t);
    }

    /**
     * Runs one iteration of the main game loop which includes processing
     * {@link Task}s and synchronization.
     */
    public void runGameLoop() {
        tasks.startEngine();
        synchronizer.preSynchronize();
        synchronizer.synchronize();
        synchronizer.postSynchronize();
    }

    /**
     * Queues {@code player} to be logged in on the next game loop.
     *
     * @param player The {@link Player} to be logged in.
     */
    public void queueLogin(Player player) {
        if (!logins.contains(player)) {
            logins.add(player);
        }
    }

    /**
     * Dequeues the {@link Queue} of {@link Player}s awaiting login.
     */
    public void dequeueLogins() {
        for (int amount = 0; amount < LOGIN_THRESHOLD; amount++) {
            Player player = logins.poll();
            if (player == null) {
                break;
            }
            players.add(player);
        }
    }

    /**
     * Queues {@code player} to be logged out on the next game loop.
     *
     * @param player The {@link Player} to be logged out.
     */
    public void queueLogout(Player player) {
        if (!logouts.contains(player)) {
            logouts.add(player);
        }
    }

    /**
     * Dequeues the {@link Queue} of {@link Player}s awaiting logout.
     */
    public void dequeueLogouts() {
        for (int amount = 0; amount < LOGIN_THRESHOLD; amount++) {
            Player player = logouts.poll();
            if (player == null) {
                break;
            }
            // TODO: Do not remove if still in combat
            players.remove(player);
        }
    }

    /**
     * Retrieves a {@link Player} instance by its {@code username}.
     *
     * @param username The username hash of the {@code Player}.
     * @return The {@code Player} instance wrapped in an {@link Optional}, or an
     * empty {@code Optional} if no {@code Player} was found.
     */
    public Optional<Player> getPlayer(long username) {
        return players.findFirst(it -> it.getUsernameHash() == username);
    }

    /**
     * Retrieves a {@link Player} instance by its {@code username}.
     *
     * @param username The username of the {@code Player}.
     * @return The {@code Player} instance wrapped in an {@link Optional}, or an
     * empty {@code Optional} if no {@code Player} was found.
     */
    public Optional<Player> getPlayer(String username) {
        return getPlayer(StringUtility.encode(username));
    }

    /**
     * @return An instance of the {@link BombermanContext}.
     */
    public BombermanContext getContext() {
        return context;
    }

    /**
     * @return The list of {@link Player}s in the lobby.
     */
    public EntityList<Player> getPlayers() {
        return players;
    }

    public void init(GameEngine engine) throws Exception {
        this.engine = engine;

        this.registerGlobalEvents();
    }

    private void registerGlobalEvents() {
        submit(new CleanupTask());
    }

    public void submit(Task task) {
        this.engine.pushTask(task);
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void setWorldId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public EntityList<Entity> getEntities() {
        // TODO Auto-generated method stub
        return null;
    }

}
