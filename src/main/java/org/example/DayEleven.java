package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.log10;

public class DayEleven {

    public static void main(String[] args) {
        System.out.println("Day 11");
        var aoc = new DayEleven();
        var stoneCount = aoc.blinkTimes(75);
        System.out.println("Stone count: " + stoneCount);
    }

    public Long blinkTimes(int count) {
        try {
            var stones = Files.readAllLines(Paths.get("src/main/resources/dayeleven.txt"))
                    .stream()
                    .map(line -> line.split("\\s+"))
                    .flatMap(Arrays::stream)
                    .map(Long::parseLong)
                    .collect(Collectors.groupingBy(
                            num -> num,
                            Collectors.counting()
                    ));

            while (count > 0) {
                stones = blink(stones);
                count--;
            }

            return stones.values().stream().mapToLong(Long::longValue).sum();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    private static Map<Long, Long> blink(Map<Long, Long> stones) {
        var next = new HashMap<Long, Long>();
        for (var entry : stones.entrySet()) {
            var stone = entry.getKey();
            var count = entry.getValue();

            if (stone == 0L) {
                addToMap(next, 1L, count);
            } else {
                var digits = (int) log10(stone) + 1;
                if (digits % 2 == 0) {
                    long divisor = (long) Math.pow(10, (double) digits / 2);
                    addToMap(next, stone / divisor, count);
                    addToMap(next, stone % divisor, count);
                } else {
                    addToMap(next, stone * 2024, count);
                }
            }
        }

        return next;
    }

    private static void addToMap(Map<Long, Long> map, Long key, Long value) {
        map.put(key, map.getOrDefault(key, 0L) + value);
    }
}
