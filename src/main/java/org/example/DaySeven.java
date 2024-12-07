package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DaySeven {
    private final Map<Long, List<Long>> numbers = new HashMap<>();

    public static void main(String[] args) {
        DaySeven daySeven = new DaySeven();

        try {
            daySeven.loadNumbers();
            daySeven.partOne();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void partOne() {
        System.out.println("Day Seven - Part One");
        long output = numbers.entrySet().stream()
                .filter(e -> reachesTarget(e.getKey(), e.getValue()))
                .mapToLong(Map.Entry::getKey)
                .sum();

        System.out.println("Output: " + output);
    }

    private boolean reachesTarget(long target, List<Long> values) {
        if (values.size() == 1) return values.getFirst().equals(target);

        var additions = new ArrayList<Long>();
        additions.add(values.getFirst() + values.get(1));
        additions.addAll(values.subList(2, values.size()));

        var mults = new ArrayList<Long>();
        mults.add(values.getFirst() * values.get(1));
        mults.addAll(values.subList(2, values.size()));

        return reachesTarget(target, additions) || reachesTarget(target, mults);
    }

    public void loadNumbers() throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dayseven.txt"));

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            var parts = line.split(":");
            var key = Long.parseLong(parts[0]);
            var values = Arrays.stream(parts[1].trim().split("\\s+")).map(Long::parseLong).toList();
            numbers.put(key, values);
        }
    }

}
