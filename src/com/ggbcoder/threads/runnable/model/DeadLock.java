package com.ggbcoder.threads.runnable.model;

public class DeadLock {

    private final Object redKey = new Object();
    private final Object greenKey = new Object();

    public void redMethod1() {
        synchronized (redKey) {
            System.out.println(Thread.currentThread().getName() + " - I'm in redMethod1()");
            greenMethod();
        }
    }

    public void greenMethod() {
        synchronized (greenKey) {
            System.out.println(Thread.currentThread().getName() + " - I'm in greenMethod()");
            redMethod2();
        }
    }

    public void redMethod2() {
        synchronized (redKey) {
            System.out.println("redMethod2");
        }
    }
}
