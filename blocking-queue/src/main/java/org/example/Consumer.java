package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final CountDownLatch startLatch;
    private final CountDownLatch endLatch;

    public Consumer(BlockingQueue<Integer> queue, CountDownLatch startLatch, CountDownLatch endLatch) {
        this.queue = queue;
        this.startLatch = startLatch;
        this.endLatch = endLatch;
    }

    @Override
    public void run() {
        try {
            startLatch.await(); // Wait for the signal to start
            for (int i = 0; i < 50; i++) {
                Integer taskId = queue.take();
                System.out.println("Consumed: " + taskId);
                Thread.sleep(150); // Simulate time taken to consume an item
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown(); // Signal that the consumer is done
        }
    }
}