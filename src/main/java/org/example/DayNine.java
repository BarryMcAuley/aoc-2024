package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayNine {

    public static void main(String[] args) {
        System.out.println("Day Nine");
        var dayNine = new DayNine();
        dayNine.partOne();
    }

    public void partOne() {
        System.out.println("Part One");
        try {
            var data = Arrays.stream(Files.readString(Path.of("src/main/resources/daynine.txt")).split(""))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            var input = calculateFileData(data);
            var listToCalc = swapSpace(input, 0, input.size() - 1).stream()
                    .filter(c -> c != -1L)
                    .toList();

            var output = 0L;
            var multiplier = 0L;
            for (Long l : listToCalc) {
                output += (l * multiplier);
                multiplier += 1;
            }

            System.out.println("Output: " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Long> swapSpace(List<Long> data, int index1, int index2) {
        var output = new ArrayList<>(data);

        while (index1 < index2) {
            var first = output.get(index1);
            var second = output.get(index2);
            if (first == -1L && second != -1L) {
                var temp = output.get(index1);
                output.set(index1, output.get(index2));
                output.set(index2, temp);
            } else if (second == -1L) {
                index2 -= 1;
            } else {
                index1 += 1;
            }
        }

        return output;
    }

    private static List<Long> calculateFileData(List<Long> data) {
        var output = new ArrayList<Long>();
        var current = 0L;
        for (int i = 0; i < data.size(); i += 2) {
            var numOfId = data.get(i);
            for (int j = 0; j < numOfId; j++) {
                output.add(current);
            }
            if (i + 1 < data.size()) {
                var numOfSpace = data.get(i + 1);
                for (int j = 0; j < numOfSpace; j++) {
                    output.add(-1L);
                }
            }

            current += 1;
        }
        return output;
    }

}
