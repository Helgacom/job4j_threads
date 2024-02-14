package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void checkCASCount() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(
                () -> IntStream.range(0, 100).forEach((i) -> casCount.increment())
        );
        Thread thread2 = new Thread(
                () -> IntStream.range(0, 100).forEach((i) -> casCount.increment())
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(casCount.get()).isEqualTo(200);
    }
}