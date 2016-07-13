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
package com.apache.game;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.apache.net.BombermanContext;
import com.apache.util.Utility;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * An {@link AbstractScheduledService} implementation that performs general game
 * logic processing, provides functionality for executing small asynchronous and
 * concurrent tasks through a cached thread pool, and allows for tasks from
 * other threads to be executed on the game logic thread.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class GameService extends AbstractScheduledService {

    /**
     * F cached thread pool that manages the execution of short, low priority,
     * asynchronous and concurrent tasks.
     */
    private final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("BombermanWorkerThread").build()));

    /**
     * F queue of synchronization tasks.
     */
    private final Queue<Runnable> syncTasks = new ConcurrentLinkedQueue<>();

    /**
     * An instance of the {@link BombermanContext}.
     */
    private final BombermanContext context;

    /**
     * Creates a new {@link GameService}.
     *
     * @param context The context this is being managed under.
     */
    public GameService(BombermanContext context) {
        this.context = context;
    }

    @Override
    protected String serviceName() {
        return "BombermanGameThread";
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>
     * This method should <b>never</b> be invoked unless by the underlying
     * {@link AbstractScheduledService}. Illegal invocation of this method will
     * lead to serious gameplay timing issues as well as other unexplainable and
     * unpredictable issues related to gameplay.
     */
    @Override
    protected void runOneIteration() throws Exception {
        try {
            for (;;) {
                Runnable t = syncTasks.poll();
                if (t == null) {
                    break;
                }

                try {
                    t.run();
                } catch (Exception e) {
                    Utility.log(e.getMessage());
                }
            }

            World world = context.getWorld();
            world.dequeueLogins();
            world.runGameLoop();
            world.dequeueLogouts();
        } catch (Exception e) {
            Utility.log(e.getMessage());
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(600, 600, TimeUnit.MILLISECONDS);
    }

    /**
     * Prints a message that this service has been terminated, and attempts to
     * gracefully exit the application cleaning up resources and ensuring all
     * players are logged out. If an exception is thrown during shutdown, the
     * shutdown process is aborted completely and the application is exited.
     */
    @Override
    protected void shutDown() {
        try {
            World world = context.getWorld();

            Utility.log("The asynchronous game service has been shutdown, exiting...");
            syncTasks.forEach(Runnable::run);
            syncTasks.clear();
            world.getPlayers().clear();
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (Exception e) {
            Utility.log(e.getMessage());
        }
        System.exit(0);
    }

    /**
     * Queues {@code t} to be executed on this game service thread.
     *
     * @param t The task to be queued.
     */
    public void sync(Runnable t) {
        syncTasks.add(t);
    }

    /**
     * Executes {@code t} using the backing cached thread pool. Tasks submitted
     * this way should generally be short and low priority.
     *
     * @param t The task to execute.
     */
    public void execute(Runnable t) {
        executorService.execute(t);
    }

    /**
     * Executes the result-bearing {@code t} using the backing cached thread
     * pool. Tasks submitted this way should generally be short and low
     * priority.
     *
     * @param t The task to execute.
     * @return The {@link ListenableFuture} to track completion of the task.
     */
    public <T> ListenableFuture<T> submit(Callable<T> t) {
        return executorService.submit(t);
    }

    /**
     * Executes {@code t} using the backing cached thread pool. Tasks submitted
     * this way should generally be short and low priority.
     *
     * @param t The task to execute.
     * @return The {@link ListenableFuture} to track completion of the task.
     */
    public ListenableFuture<?> submit(Runnable t) {
        return executorService.submit(t);
    }

    /**
     * @return An instance of the {@link BombermanContext}.
     */
    public BombermanContext getContext() {
        return context;
    }
}
