package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThree {

    public static void main(String[] args) throws IOException {
        Pattern partOne = Pattern.compile("mul\\(\\d+,\\s*\\d+\\)");
        Pattern partTwo = Pattern.compile("(mul\\(\\d+,\\s*\\d+\\))|(do|don't)\\(\\)");


        calculateMul(partOne);
        calculateMul(partTwo);
    }

    private static void calculateMul(Pattern regex) throws IOException {
        String content = Files.readString(Path.of("src/main/resources/daythree.txt"));
        Matcher matcher = regex.matcher(content);

        var results = new ArrayList<Integer>();
        var active = true;
        while (matcher.find()) {
            var match = matcher.group();

            if (match.contains("don't")) {
                active = false;
                continue;
            } else if (match.contains("do")) {
                active = true;
                continue;
            }

            if (active) {
                var values = match.replaceAll("[^0-9,]", "").split(",");
                results.add(Integer.parseInt(values[0]) * Integer.parseInt(values[1]));
            }
        }
        System.out.println(results.stream().mapToInt(Integer::intValue).sum());
    }
}
