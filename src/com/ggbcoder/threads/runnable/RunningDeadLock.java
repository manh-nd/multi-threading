package com.ggbcoder.threads.runnable;

import com.ggbcoder.threads.runnable.model.DeadLock;

public class RunningDeadLock {
    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();

        Runnable task1 = deadLock::redMethod1;
        Runnable task2 = deadLock::greenMethod;

        Thread t1 = new Thread(task1, "Red");
        Thread t2 = new Thread(task2, "Green");

        new Thread(() -> {
            while(true) {
                try {
                    Thread.State t1State = t1.getState();
                    Thread.State t2State = t2.getState();
                    System.out.println("t1: " + t1State.name());
                    System.out.println("t2: " + t2State.name());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
