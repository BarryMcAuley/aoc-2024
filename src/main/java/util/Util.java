package util;

import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static void printGrid(List<List<Character>> grid) {
        System.out.println("Grid:");
        grid.forEach(row -> System.out.println(
                row.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining())
        ));
    }
}
