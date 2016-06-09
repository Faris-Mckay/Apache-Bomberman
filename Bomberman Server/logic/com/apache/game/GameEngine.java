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

import com.apache.engine.Task;
import com.apache.util.BlockingExecutorService;

public class GameEngine implements Runnable {

	private final BlockingQueue<Task> tasks = new LinkedBlockingQueue<Task>();

	private final ScheduledExecutorService logicService = Executors.newScheduledThreadPool(3);

	private final BlockingExecutorService taskService = new BlockingExecutorService(
			Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

	private final ExecutorService workService = Executors.newSingleThreadExecutor();

	private boolean running = false;

	private Thread thread;

	/**
	 * In theory, we can access these games by their id without having to worry
	 * about which thread is being used to create or access them. Some potential
	 * uses for this could be gathering statistics or viewing these matches in
	 * real-time.
	 */
	private Vector<AtomicInteger> numberOfGamesInProgress = new Vector<AtomicInteger>();

	public void pushTask(Task task) {
		tasks.offer(task);
	}

	public boolean isRunning() {
		return running;
	}

	public void start() {
		if (running) {
			throw new IllegalStateException("The engine is already running.");
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

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