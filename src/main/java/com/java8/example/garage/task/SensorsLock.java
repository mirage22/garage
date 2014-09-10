package com.java8.example.garage.task;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by miroslavkopecky on 10/09/14.
 *
 * GarageLock extends the ReentrantLock class.
 * Reason is to convert in public two  protected methods.
 * getOwnerName() returns the name of the Sensors thread that have the control
 * of the lock and uses the protected method getOwner();
 * getThreads() returns the list of Sensor threads queued in the lock and uses
 * the protected method getQueuedThreads();

 */
public class SensorsLock extends ReentrantLock {

    /**
     * Declare the serial version uid of the class
     */
    private static final long serialVersionUID = 22L;

    /**
     * This method return the name of the thread that have the control of the Lock
     * of the constant "None". if the Lock is free.
     * @return The name of the thread that has the control of the lock
     */
    public String getOwnerName(){
        if(this.getOwner() == null){
            return "None";
        }
        return this.getOwner().getName();
    }

    /**
     * Returns the list of the thread queued in the lock
     * @return
     */
    public Collection<Thread> getThreads(){
        return this.getQueuedThreads();
    }
}
