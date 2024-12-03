package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DayTwo {

    private static final List<List<Integer>> numbers = new ArrayList<>();

    public static void loadNumbers() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/daytwo.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            numbers.add(Arrays.stream(line.split("\\s+"))
                    .map(Integer::parseInt)
                    .toList()
            );
        }
    }

    public static void partOne() {
        AtomicInteger output = new AtomicInteger();
        numbers.forEach(list -> {
            var isValid = isIncreasingOrDecreasing(list) && isValidLevel(list);
            if (isValid) {
                output.incrementAndGet();
            }

        });

        System.out.println(output.get());
    }

    private static boolean isValidLevel(List<Integer> list) {
        var left = 0;
        var right = list.size() - 1;

        while (left < right) {
            var valOne = list.get(left);
            var valTwo = list.get(right);
            var nextLeft = list.get(left + 1);
            var nextRight = list.get(right - 1);

            if (nextLeft.equals(valOne) || nextRight.equals(valTwo)) {
                return false;
            }

            if (valOne > valTwo) {
                if (valOne - nextLeft > 3 || nextRight - valTwo > 3) {
                    return false;
                }
            } else {
                if (nextLeft - valOne > 3 || valTwo - nextRight > 3) {
                    return false;
                }
            }

            left++;
            right--;
        }
        return true;
    }

    public static void partTwo() {
        AtomicInteger output = new AtomicInteger();
        numbers.forEach(list -> {
            var isValid = isIncreasingOrDecreasing(list) && isValidLevel(list);
            if (!isValid) {
                for (int i = 0; i < list.size(); i++) {
                    var copy = new ArrayList<>(list);
                    copy.remove(i);
                    if (isIncreasingOrDecreasing(copy) && isValidLevel(copy)) {
                        isValid = true;
                        break;
                    }
                }
            }

            if (isValid) {
                output.incrementAndGet();
            }

        });

        System.out.println(output.get());
    }

    public static void main(String[] args) throws IOException {
        loadNumbers();
        partOne();
        partTwo();
    }

    private static boolean isIncreasingOrDecreasing(List<Integer> list) {
        boolean isValid;
        isValid = IntStream.range(0, list.size() - 1).allMatch(i -> list.get(i) > list.get(i + 1));
        if (!isValid) {
            isValid = IntStream.range(0, list.size() - 1).allMatch(i -> list.get(i) < list.get(i + 1));
        }
        return isValid;
    }
}