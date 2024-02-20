package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    public void whenStringShortArrayThanFindIndex() {
        String[] array = new String[] {"aa", "bb", "cc", "dd", "ee"};
        String object = "cc";
        ParallelIndexSearch<String> pis = new ParallelIndexSearch<>(array, object, 0, 4);
        assertThat(pis.search(array, object)).isEqualTo(2);
    }

    @Test
    public void whenStringShortArrayThanNotFindIndex() {
        String[] array = new String[] {"aa", "bb", "cc", "dd", "ee"};
        String object = "ff";
        ParallelIndexSearch<String> pis = new ParallelIndexSearch<>(array, object, 0, 4);
        assertThat(pis.search(array, object)).isEqualTo(-1);
    }

    @Test
    public void whenIntegerRecursiveArrayThanFindIndex() {
        Integer[] array = new Integer[] {11, -8, 3, 1, 16, -44, 5, 32, 4, 22, -15, 0, 7, 19};
        int object = -15;
        ParallelIndexSearch<Integer> pis = new ParallelIndexSearch<>(array, object, 0, 13);
        assertThat(pis.search(array, object)).isEqualTo(10);
    }

    @Test
    public void whenFloatRecursiveArrayThanIndex() {
        Float[] array = new Float[] {11f, -8f, 3f, 1f, 16f, -44f, 5f, 32f, 4f, 22f, -15f, 0f, 7f, 19f};
        float object = -15f;
        ParallelIndexSearch<Float> pis = new ParallelIndexSearch<>(array, object, 0, 13);
        assertThat(pis.search(array, object)).isEqualTo(10);
    }

}