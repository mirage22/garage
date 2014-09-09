package com.java8.example.garage.task;

/**
 * Created by miroslavkopecky on 09/09/14.
 *
 * Task always throws exception
 *
 */
public class ExceptionTask implements Runnable{
    @Override
    public void run() {
        int justException=Integer.parseInt("GETME");
    }
}
