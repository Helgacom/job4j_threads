package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T[] objects;
    private final T object;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] objects, T object, int from, int to) {
        this.objects = objects;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return indexSearch();
        }
        int middle = (from + to) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(objects, object, from, middle);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(objects, object, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public int indexSearch() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (object.equals(objects[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static <T> int search(T[] objects, T object) {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new ParallelIndexSearch<>(objects, object, 0, objects.length - 1));
    }
}
