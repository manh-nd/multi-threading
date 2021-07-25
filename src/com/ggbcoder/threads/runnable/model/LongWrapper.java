package com.ggbcoder.threads.runnable.model;

public class LongWrapper {
    private final Object key = new Object();
    private long value;

    public LongWrapper(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void incrementValue() {
        synchronized (key) {
            value = value + 1;
        }
    }
}
