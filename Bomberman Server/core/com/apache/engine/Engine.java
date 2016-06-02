/* 
 * This file is property of Apache-GS.
 *
 * Copyright (C) Apache-GS, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential.
 *
 * Further information can be acquired regarding the licensing of this product 
 * Apache-GS (C). In the project license directory.
 * Written by Faris McKay <faris.mckay@hotmail.com>, May 2016
 *
 */
package com.apache.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Faris <https://github.com/faris-mckay>
 */
public class Engine {
    
    //the time in ms to pass before each engine cycle
    private static final int ENGINE_BASE_CYCLE_RATE = 500;
    
    //stores the amount of cycles the engine has been alive for
    private int cyclesPassed;
    
    //contains the tasks submitted to the engine
    private ArrayList<Task> tasks = new ArrayList();
    
    /**
     * Begin the engine server of executing tasks
     */
    public void startEngine(){
        run();
    }
    
    /**
     * Places a task into execution for inclusion in future loops. Does not execute till
     * next available cycle
     * @param task the created task for execution
     */
    public void submit(Task task){
        getTasks().add(task);
    }
    
    /**
     * Work through each task and handle if they should execute or not, and if they should be removed
     * from the engine or not, and processes the Tasks which need to be
     * @param task 
     */
    private void processTask(Task task){
        if(getCyclesPassed() % task.getCycleRate() == 0)
            task.execution();
    }
    
    /**
     * Begin the engine process of checking for tasks and executing tasks which require execution this
     * is based on their shouldExecute boolean, all calculations are done in respective Tasks, and they
     * set themselves ready when it is time to execute again
     */
    private void run(){
        Task task;
        while(true){
            Iterator<Task> it = getTasks().iterator();
            while(it.hasNext()){
                try {
                    task = it.next();
                    if(task.shouldStop()){
                        it.remove();
                        continue;
                    }
                    processTask(task);
                    addCyclePassed();
                    Thread.sleep(ENGINE_BASE_CYCLE_RATE);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    

    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the cyclesPassed
     */
    public int getCyclesPassed() {
        return cyclesPassed;
    }

    /**
     * increment the cycles passed
     */
    public void addCyclePassed() {
        this.cyclesPassed = ++cyclesPassed;
    }
    
}
