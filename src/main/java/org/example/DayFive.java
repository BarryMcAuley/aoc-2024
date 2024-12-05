package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DayFive {
    private final Map<Integer, Set<Integer>> pageWeights = new HashMap<>();
    private final List<List<Integer>> updates = new ArrayList<>();

    public static void main(String[] args) {
        DayFive dayFive = new DayFive();

        try {
            dayFive.loadNumbers();
            dayFive.partOne();
            dayFive.partTwo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNumbers() throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dayfive.txt"));

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            if (line.contains("|")) {
                var nums = Arrays.stream(line.split("\\|")).map(Integer::parseInt).toList();
                pageWeights.computeIfAbsent(nums.get(0), k -> new HashSet<>()).add(nums.get(1));
            } else {
                updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            }
        }
    }

    public void partOne() throws IOException {
        System.out.println("Day Five - Part One");

        var output = new ArrayList<Integer>();
        for (var update : updates) {
            if (update.stream()
                    .sorted(
                            (Integer a, Integer b) -> {
                                if (pageWeights.containsKey(a) && pageWeights.get(a).contains(b)) {
                                    return -1;
                                } else if (pageWeights.containsKey(b) && pageWeights.get(b).contains(a)) {
                                    return 1;
                                }

                                return Integer.compare(a, b);
                            })
                    .toList()
                    .equals(update)) {
                output.add(update.get(update.size() / 2));
            }
        }

        System.out.println(output.stream().mapToInt(Integer::intValue).sum());
    }

    public void partTwo() {
        System.out.println("Day Five - Part Two");

        var output = new ArrayList<Integer>();
        for (var update : updates) {
            var sorted = update.stream()
                    .sorted(
                            (Integer a, Integer b) -> {
                                if (pageWeights.containsKey(a) && pageWeights.get(a).contains(b)) {
                                    return -1;
                                } else if (pageWeights.containsKey(b) && pageWeights.get(b).contains(a)) {
                                    return 1;
                                }

                                return Integer.compare(a, b);
                            })
                    .toList();
            if (!sorted.equals(update)) {
                output.add(sorted.get(sorted.size() / 2));
            }
        }

        System.out.println(output.stream().mapToInt(Integer::intValue).sum());
    }
}
