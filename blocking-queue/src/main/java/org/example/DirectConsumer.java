package org.example;

import java.util.concurrent.CountDownLatch;

public class DirectConsumer implements Runnable {

    private final SharedResource sharedResource;
    private final CountDownLatch startLatch;
    private final CountDownLatch endLatch;

    public DirectConsumer(SharedResource sharedResource, CountDownLatch startLatch, CountDownLatch endLatch) {
        this.sharedResource = sharedResource;
        this.startLatch = startLatch;
        this.endLatch = endLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            sharedResource.consume(startLatch, endLatch);
            try {
                Thread.sleep(150); // Simulate time taken to consume an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
