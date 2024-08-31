package org.example;

import java.util.concurrent.CountDownLatch;

public class DirectProducer implements Runnable {

    private final SharedResource sharedResource;
    private final CountDownLatch startLatch;
    private final CountDownLatch endLatch;

    public DirectProducer(SharedResource sharedResource, CountDownLatch startLatch, CountDownLatch endLatch) {
        this.sharedResource = sharedResource;
        this.startLatch = startLatch;
        this.endLatch = endLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            sharedResource.produce(i, startLatch, endLatch);
            try {
                Thread.sleep(100); // Simulate time taken to produce an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
