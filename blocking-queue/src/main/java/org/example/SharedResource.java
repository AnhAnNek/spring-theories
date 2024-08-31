package org.example;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class SharedResource {

    private final Queue<Integer> taskQueue = new ConcurrentLinkedQueue<>();
    private final int maxCapacity;

    public SharedResource(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Producer method to add tasks
    public void produce(int taskId, CountDownLatch startLatch, CountDownLatch endLatch) {
        try {
            startLatch.await(); // Wait for the signal to start
            if (taskQueue.size() < maxCapacity) {
                taskQueue.add(taskId);
                System.out.println("Produced: " + taskId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown(); // Signal that the producer is done
        }
    }

    // Consumer method to take tasks
    public void consume(CountDownLatch startLatch, CountDownLatch endLatch) {
        try {
            startLatch.await(); // Wait for the signal to start
            Integer taskId = taskQueue.poll();
            if (taskId != null) {
                System.out.println("Consumed: " + taskId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown(); // Signal that the consumer is done
        }
    }
}
