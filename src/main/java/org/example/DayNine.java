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
        dayNine.partTwo();
    }

    public void partOne() {
        System.out.println("Part One");
        try {
            var data = readInputFile();
            var input = calculateFileData(data);
            var listToCalc = swapSpace(input, 0, input.size() - 1).stream()
                    .filter(c -> c != -1L)
                    .toList();
            var output = calculateChecksum(listToCalc);

            System.out.println("Output: " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void partTwo() {
        System.out.println("Part Two");
        try {
            var data = readInputFile();
            var input = calculateFileData(data);
            var listToCalc = optimisedSwapSpace(input, input.size());
            var output = calculateChecksum(listToCalc);

            System.out.println("Output: " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long calculateChecksum(List<Long> listToCalc) {
        var output = 0L;
        var multiplier = 0L;
        for (Long l : listToCalc) {
            if (l != -1L) {
                output += (l * multiplier);
            }
            multiplier += 1;
        }
        return output;
    }

    private static List<Long> readInputFile() throws IOException {
        return Arrays.stream(Files.readString(Path.of("src/main/resources/daynine.txt")).split(""))
                .map(Long::parseLong)
                .collect(Collectors.toList());
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

    private static List<Long> optimisedSwapSpace(List<Long> data, int end) {
        var output = new ArrayList<>(data);

        while (end > 0) {
            while (end > 0 && output.get(end - 1) == -1L) {
                end--;
            }
            if (end == 0) {
                break;
            }

            var fileID = output.get(end - 1);
            var start = end - 1;
            while (start > 0 && output.get(start - 1).equals(fileID)) {
                start--;
            }
            var idLen = end - start;

            var freeSpace = findFreeSpace(output, idLen, start);
            if (freeSpace != -1) {
                for (int i = 0; i < idLen; i++) {
                    var temp = output.get(freeSpace + i);
                    output.set(freeSpace + i, output.get(start + i));
                    output.set(start + i, temp);
                }
            }

            end = start;
        }

        return output;
    }

    private static int findFreeSpace(List<Long> data, int len, int max) {
        int consecutiveSpaces = 0;
        int startPosition = -1;

        for (int i = 0; i < max; i++) {
            if (data.get(i) == -1L) {
                if (consecutiveSpaces == -1) {
                    startPosition = i;
                    consecutiveSpaces = 1;
                } else {
                    consecutiveSpaces++;
                }

                if (consecutiveSpaces >= len) {
                    return startPosition;
                }
            } else {
                consecutiveSpaces = -1;
            }
        }

        return -1;
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
