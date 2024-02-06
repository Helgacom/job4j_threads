package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void checkExecuteProducerAndConsumerThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> IntStream.range(1, 10).forEach((i) -> {
                    try {
                        queue.offer(i);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
        Thread consumer = new Thread(
                () -> IntStream.range(1, 9).forEach((i) -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo(9);
    }
}