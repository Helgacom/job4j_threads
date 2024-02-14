package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int value;
        int rsl;
        do {
            value = count.get();
            rsl = value + 1;
        } while (!count.compareAndSet(value, rsl));
    }

    public int get() {
        return count.get();
    }
}
