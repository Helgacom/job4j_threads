package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        var rslSums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            var sums = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
                sums.setRowSum(rowSum);
                sums.setColSum(colSum);
                rslSums[i] = sums;
            }
        }
        return rslSums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(i, getSumTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getSumTask(int[][] data, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            Arrays.stream(data[i])
                    .forEach(n -> sums.setRowSum(sums.getRowSum() + n));
            Arrays.stream(data)
                    .forEach(row -> sums.setColSum(sums.getColSum() + row[i]));
            return sums;
        });
    }
}
