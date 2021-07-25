package com.ggbcoder.threads.runnable;

public class ConsumerProducerPattern {

    static final Object lock = new Object();
    static int[] buffer;
    static int count;

    static class Consumer {
        void consume() {
            synchronized (lock) {
                if(isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                buffer[--count] = 0;
                lock.notify();
            }
        }
    }

    static class Producer {
        void produce() {
            synchronized (lock) {
                if(isFull()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                buffer[count++] = 1;
                lock.notify();
            }
        }
    }

    static boolean isEmpty() {
        return count == 0;
    }

    static boolean isFull() {
        return count == buffer.length - 1;
    }

    static int size() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        buffer = new int[10];
        count = 0;

        Consumer consumer = new Consumer();
        Producer producer = new Producer();

        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                producer.produce();
            }
            System.out.println("Produce task has done!");
        });

        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 45; i++) {
                consumer.consume();
            }
            System.out.println("Consume task has done!");
        });

        producerThread.start();
        consumerThread.start();

        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!consumerThread.isAlive()) {
                    break;
                }

                if(producerThread.isAlive()) {
                    System.out.println("Producer Thread still alive!");
                    continue;
                }

                if(consumerThread.getState() == Thread.State.WAITING) {
                    System.out.println("Producer produces one more item");
                    producer.produce();
                }
            }
        }).start();

        producerThread.join();
        consumerThread.join();

        System.out.println("Buffer size: " + size());
    }
}
