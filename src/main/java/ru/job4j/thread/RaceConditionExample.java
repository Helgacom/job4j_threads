package ru.job4j.thread;

public class RaceConditionExample {
    /*public static int number = 0;

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 99999; i++) {
                int current = number;
                int next = ++number;
                if (current + 1 != next) {
                    throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
                }
            }
        };
        new Thread(task).start();
        new Thread(task).start();
    }*/
    private int number = 0;

    public synchronized void increment() {
        for (int i = 0; i < 99999; i++) {
            int current = number;
            int next = number + 1;
            if (current + 1 != next) {
                throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionExample raceCondition = new RaceConditionExample();
        Thread one = new Thread(raceCondition::increment);
        one.start();
        Thread two = new Thread(raceCondition::increment);
        two.start();
        one.join();
        two.join();
    }
}
