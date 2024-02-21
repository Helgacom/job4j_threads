package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RowColSumTest {

    @Test
    void whenRowColSumCountsWithSumThanSums() {
        int[][] data = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = RowColSum.sum(data);
        assertThat(sums.length).isEqualTo(3);
        assertThat(sums[2].getRowSum()).isEqualTo(24);
        assertThat(sums[2].getColSum()).isEqualTo(18);
    }

    @Test
    void whenRowColSumCountsWithAsyncSumThanSums() throws ExecutionException, InterruptedException {
        int[][] data = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = RowColSum.asyncSum(data);
        assertThat(sums.length).isEqualTo(3);
        assertThat(sums[2].getRowSum()).isEqualTo(24);
        assertThat(sums[2].getColSum()).isEqualTo(18);
    }

}