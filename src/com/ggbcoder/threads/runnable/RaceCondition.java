package com.ggbcoder.threads.runnable;

import com.ggbcoder.threads.runnable.model.LongWrapper;

class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        LongWrapper longWrapper = new LongWrapper(0L);

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1_000; i++) {
                longWrapper.incrementValue();
            }
        };

        Thread[] threads = new Thread[1_000];

        for (int i = 0, threadsLength = threads.length; i < threadsLength; i++) {
            threads[i] = new Thread(incrementTask);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(longWrapper.getValue());
    }
}
