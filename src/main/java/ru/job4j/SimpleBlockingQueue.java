package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private int maxSize;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public SimpleBlockingQueue() {
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= maxSize) {
            this.wait();
        }
        queue.offer(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }
}
