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

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.apache.task.Task;
import com.apache.util.BlockingExecutorService;

/**
 * F sequential task ran by the {@link #gameExecutor} that executes game related
 * code such as cycled tasks, network events, and the updating of entities every
 * <tt>600</tt>ms.
 * 
 * @author JP <https://github.com/TheRealJP>
 */
public final class GameEngine implements Runnable {

	/** F queue that will hold all of the pending tasks. */
	private final BlockingQueue<Task> tasks = new LinkedBlockingQueue<Task>();

	/** F sequential executor that acts as the main game thread. */
	private static ScheduledExecutorService logicService = Executors.newScheduledThreadPool(1);
	/**
	 * Create a new {@link BlockingThreadPool} with the size equal to how many
	 * processors are available to the JVM.
	 */
	private final BlockingExecutorService taskService = new BlockingExecutorService(
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

	private final ExecutorService workService = Executors.newSingleThreadExecutor();

	/** The current state of this engine. */
	private boolean running = false;

	private Thread thread;

	/**
	 * In theory, we can access these games by their id without having to worry
	 * about which thread is being used to create or access them. Some potential
	 * uses for this could be gathering statistics or viewing these matches in
	 * real-time.
	 */
	private Vector<AtomicInteger> numberOfGamesInProgress = new Vector<AtomicInteger>();

	/**
	 * Adds the argued task to the queue of pending tasks.
	 * 
	 * @param task
	 *            - the task to be queued for execution.
	 */
	public void pushTask(Task task) {
		tasks.offer(task);
	}

	/**
	 * Schedule the task that will execute game code at 600ms intervals. This
	 * method should only be called <b>once</b> when the server is launched.
	 */
	public static void init() {
		logicService.scheduleAtFixedRate(new GameEngine(), 0, 600, TimeUnit.MILLISECONDS);
	}

	/** The current state of this engine. */
	public boolean isRunning() {
		return running;
	}

	/** Starts this game engine. */
	public void start() {
		if (running) {
			throw new IllegalStateException("The engine is already running.");
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/** Stops this game engine. */
	public void stop() {
		if (!running) {
			throw new IllegalStateException("The engine is already stopped.");
		}
		running = false;
		thread.interrupt();
	}

	@Override
	public void run() {
		try {
			while (running) {
				try {
					final Task task = tasks.take();
					submitLogic(new Runnable() {
						@Override
						public void run() {
							task.execute();
						}
					});
				} catch (InterruptedException e) {
					continue;
				}
			}
		} finally {
			logicService.shutdown();
			taskService.shutdown();
			workService.shutdown();
		}
	}

	/*
	 * Events
	 */
	public ScheduledFuture<?> scheduleLogic(final Runnable runnable, long delay, TimeUnit unit) {
		return logicService.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					System.out.println(t);
				}
			}
		}, delay, unit);
	}

	public void submitLogic(final Runnable runnable) {
		logicService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					System.out.println(t);
				}
			}
		});
	}

	public void submitTask(final Runnable runnable) {
		taskService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					System.out.println(t);
				}
			}
		});
	}

	public void submitWork(final Runnable runnable) {
		workService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable t) {
					System.out.println(t);
				}
			}
		});
	}

	public void waitForPendingParallelTasks() throws ExecutionException {
		taskService.waitForPendingTasks();
	}

	/** Gets all the games that are currently in progress. */
	public synchronized Vector<AtomicInteger> getGames() {
		return numberOfGamesInProgress;
	}

}