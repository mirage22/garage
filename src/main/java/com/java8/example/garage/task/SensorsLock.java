package com.java8.example.garage.task;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
/*
 * The MIT License
 *
 * Copyright 2014 Miroslav Kopecky.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
