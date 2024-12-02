package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DayOne {

    private static final PriorityQueue<Integer> pqOne = new PriorityQueue<>();
    private static final PriorityQueue<Integer> pqTwo = new PriorityQueue<>();
    private static final Map<Integer, Integer> occurrences = new HashMap<>();

    public static void loadNumbers() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lists.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] numbers = line.split("\\s+");
            var left = Integer.parseInt(numbers[0]);
            var right = Integer.parseInt(numbers[1]);
            pqOne.offer(left);
            pqTwo.offer(right);

            occurrences.put(right, occurrences.getOrDefault(right, 0) + 1);
        }
    }

    public static void main(String[] args) {
        try {
            loadNumbers();

            var diffs = new ArrayList<Integer>();
            var similarityScores = new ArrayList<Integer>();
            while (!pqOne.isEmpty() && !pqTwo.isEmpty()) {
                var valOne = pqOne.poll();
                var valTwo = pqTwo.poll();

                diffs.add(Math.abs(valOne - valTwo));
                similarityScores.add(valOne * occurrences.getOrDefault(valOne, 0));
            }

            // Return the sums
            System.out.println(diffs.stream().mapToInt(Integer::intValue).sum());
            System.out.println(similarityScores.stream().mapToInt(Integer::intValue).sum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}