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
package com.apache.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.apache.util.Utility;

/**
 * An abstraction model that contains functions that enable units of work to be
 * carried out in cyclic intervals.
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class TaskEngine {

    // the time in ms to pass before each engine cycle
    private static final int ENGINE_BASE_CYCLE_RATE = 500;

    // stores the amount of cycles the engine has been alive for
    private int counter;

    // contains the tasksAwaitingExecution submitted to the engine
    private List<Task> tasksAwaitingExecution = new ArrayList<Task>();

    /**
     * Begin the engine server of executing tasksAwaitingExecution
     */
    public void startEngine() {
        run();
    }

    /**
     * Places a task into execution for inclusion in future loops. Does not
     * execute till next available cycle
     *
     * @param task the created task for execution
     */
    public void submit(Task task) {
        getTasks().add(task);
    }

    /**
     * Work through each task and handle if they should execute or not, and if
     * they should be removed from the engine or not, and processes the Tasks
     * which need to be
     *
     * @param task
     */
    private void processTask(Task task) {
        if (getCyclesPassed() % task.getCycleRate() == 0) {
            task.execution();
        }
    }

    /**
     * Begin the engine process of checking for tasksAwaitingExecution and
     * executing tasksAwaitingExecution which require execution this is based on
     * their shouldExecute boolean, all calculations are done in respective
     * Tasks, and they set themselves ready when it is time to execute again
     */
    private void run() {
        Task task;
        while (true) {
            Iterator<Task> it = getTasks().iterator();
            while (it.hasNext()) {
                try {
                    task = it.next();
                    if (task.shouldStop()) {
                        it.remove();
                        continue;
                    }
                    processTask(task);
                    addCyclePassed();
                    Thread.sleep(ENGINE_BASE_CYCLE_RATE);
                } catch (InterruptedException ex) {
                    Utility.log(ex.getMessage());
                }
            }
        }
    }

    /**
     * Iterates through all active {@link Task}s and cancels all that have
     * {@code attachment} as their attachment.
     */
    public void cancel(Object attachment) {
        tasksAwaitingExecution.stream().filter(it -> Objects.equals(attachment, it.getAttachment().orElse(null)))
                .forEach(Task::shouldStop);
    }

    /**
     * @return the tasksAwaitingExecution
     */
    public List<Task> getTasks() {
        return tasksAwaitingExecution;
    }

    /**
     * @return the counter
     */
    public int getCyclesPassed() {
        return counter;
    }

    /**
     * increment the cycles passed
     */
    public void addCyclePassed() {
        this.counter += 1;
    }

}
