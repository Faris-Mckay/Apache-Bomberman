package com.apache.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import com.apache.game.entity.Entity;
import com.apache.game.entity.EntityType;
import com.apache.game.entity.player.Player;
import com.apache.util.Utility;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Synchronizes all of the {@link Player}s and {@link Entities}s with the
 * {@link World} through the updating protocol. The entire process except for
 * pre-synchronization is done in parallel, effectively utilizing as much of the
 * host computer's CPU as possible for maximum performance.
 *
 * TODO: Sync all logic for each individual game.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class WorldSynchronizer {

    // TODO: Maybe rename this to something more fitting?
    /**
     * F {@code Runnable} implementation that executes some sort of logic while
     * handling thread-safety for a {@link Entity}, {@code Exception}s, as well
     * as synchronization barriers.
     */
    private abstract class SynchronizationTask implements Runnable {

        /**
         * The {@link Entity} to synchronize over.
         */
        private final Entity entity;

        /**
         * Creates a new {@link SynchronizationTask}.
         *
         * @param entity The {@link Entity} to synchronize over.
         */
        public SynchronizationTask(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void run() {
            synchronized (entity) {
                try {
                    execute();
                } catch (Exception e) {
                    Utility.log(e.getMessage());
                    remove();
                } finally {
                    synchronizer.arriveAndDeregister();
                }
            }
        }

        /**
         * The logic to execute within this task.
         */
        public abstract void execute();

        /**
         * Removes the {@code entity} if an {@code Exception} is thrown.
         */
        private void remove() {
            if (entity.type() == EntityType.PLAYER) {
                Player player = (Player) entity;
                player.logout();
            } else {
                throw new IllegalStateException("should never reach here");
            }
        }
    }

    /**
     * The {@link World} instance.
     */
    private final World world;

    /**
     * F synchronization barrier that will ensure the main game thread waits for
     * the {@code updateExecutor} threads to finish executing
     * {@link SynchronizationTask}s before proceeding.
     */
    private final Phaser synchronizer = new Phaser(1);

    /**
     * An {@link ExecutorService} that will execute {@link SynchronizationTask}s
     * in parallel.
     */
    private final ExecutorService updateExecutor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadFactoryBuilder().setNameFormat("WorldSynchronizerThread").build());

    /**
     * Creates a new {@link WorldSynchronizer}.
     *
     * @param world The {@link World} instance.
     */
    public WorldSynchronizer(World world) {
        this.world = world;
    }

    /**
     * Pre-synchronization, update the walking queue and perform miscellaneous
     * processing that requires cyclic execution. This is
     * <strong>generally</strong> not safe to do in parallel.
     */
    public void preSynchronize() {
        world.getPlayers().forEach(it -> {
            try {
                // check path here
                it.getSession().dequeue();
                // some logic here
            } catch (Exception e) {
                it.logout();
                Utility.log(e.getMessage());
            }
        });

    }

    /**
     * Synchronization, send the {@link Player} and {@link Entity} updating
     * messages for all online {@code Player}s in parallel. TODO: For each game.
     */
    public void synchronize() {
        synchronizer.bulkRegister(world.getPlayers().size());
        world.getPlayers().forEach(it -> updateExecutor.execute(new SynchronizationTask(it) {
            @Override
            public void execute() {
                // update all entities here
            }
        }));
        synchronizer.arriveAndAwaitAdvance();
    }

    /**
     * Post-synchronization, clear various flags. This can be done safely in
     * parallel.
     */
    public void postSynchronize() {
        synchronizer.bulkRegister(world.getPlayers().size());
        world.getPlayers().forEach(it -> updateExecutor.execute(new SynchronizationTask(it) {
            @Override
            public void execute() {
                // it.clearFlags();
                it.setCachedBlock(null);
            }
        }));
        synchronizer.arriveAndAwaitAdvance();

        synchronizer.bulkRegister(world.getEntities().size());
        world.getEntities().forEach(it -> updateExecutor.execute(new SynchronizationTask(it) {
            @Override
            public void execute() {
                // it.clearFlags();
            }
        }));
        synchronizer.arriveAndAwaitAdvance();
    }
}
