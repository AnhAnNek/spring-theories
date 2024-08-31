package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class BenchmarkComparison {

    private static final int CAPACITY = 10;
    private static final int NUM_PRODUCERS = 2;
    private static final int NUM_CONSUMERS = 3;
    private static final int NUM_TASKS = 100;

    public static void main(String[] args) throws InterruptedException {
        // Queue-based benchmark
        long queueTime = benchmarkQueueApproach();
        System.out.println("Queue-based approach time: " + queueTime + " ms");

        // No-queue benchmark
        long noQueueTime = benchmarkNoQueueApproach();
        System.out.println("No-Queue approach time: " + noQueueTime + " ms");
    }

    private static long benchmarkQueueApproach() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_PRODUCERS + NUM_CONSUMERS);

        // Start producers and consumers
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            new Thread(new Producer(queue, startLatch, endLatch)).start();
        }
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            new Thread(new Consumer(queue, startLatch, endLatch)).start();
        }

        long startTime = System.currentTimeMillis();
        startLatch.countDown(); // Start all threads
        endLatch.await(); // Wait for all threads to finish
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    private static long benchmarkNoQueueApproach() throws InterruptedException {
        SharedResource sharedResource = new SharedResource(CAPACITY);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_PRODUCERS + NUM_CONSUMERS);

        // Start producers and consumers
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            new Thread(new DirectProducer(sharedResource, startLatch, endLatch)).start();
        }
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            new Thread(new DirectConsumer(sharedResource, startLatch, endLatch)).start();
        }

        long startTime = System.currentTimeMillis();
        startLatch.countDown(); // Start all threads
        endLatch.await(); // Wait for all threads to finish
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
