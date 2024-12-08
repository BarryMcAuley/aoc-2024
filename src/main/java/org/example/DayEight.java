package org.example;

import util.Point;

import java.util.*;
import java.util.stream.Collectors;

public class DayEight {

    public static void main(String[] args) {
        System.out.println("Day Eight");
        var dayEight = new DayEight();
        dayEight.partOne();
        dayEight.partTwo();
    }

    private boolean isInBounds(int x, int y, List<List<Character>> grid) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.getFirst().size();
    }

    private void printGrid(List<List<Character>> grid) {
        System.out.println("Grid:");
        grid.forEach(row -> System.out.println(
                row.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining())
        ));
    }

    private Map<Character, List<Point>> loadAntennae(List<List<Character>> grid) {
        var antennae = new HashMap<Character, List<Point>>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j) != '.') {
                    antennae.putIfAbsent(grid.get(i).get(j), new ArrayList<>());
                    antennae.get(grid.get(i).get(j)).add(new Point(i, j));
                }
            }
        }
        return antennae;
    }

    public void partOne() {
        System.out.println("Part One");
        var grid = util.Files.readLines("src/main/resources/dayeight.txt");
        var antennae = loadAntennae(grid);
        var antinodes = getAntinodes(antennae, grid, false);

        printGrid(grid);
        System.out.println(antinodes.size());
    }

    private void partTwo() {
        System.out.println("Part Two");
        var grid = util.Files.readLines("src/main/resources/dayeight.txt");
        var antennae = loadAntennae(grid);
        var antinodes = getAntinodes(antennae, grid, true);

        printGrid(grid);
        System.out.println(antinodes.size());
    }

    private HashSet<Point> getAntinodes(Map<Character, List<Point>> antennae, List<List<Character>> grid, boolean resonantHarmonics) {
        var antinodes = new HashSet<Point>();
        for (var entry : antennae.entrySet()) {
            var points = entry.getValue();

            for (var point : points) {
                for (var otherPoint : points) {
                    if (point.equals(otherPoint)) {
                        continue;
                    }

                    var dist = point.subtract(otherPoint);
                    var antinode = point.add(dist);
                    var antinodeTwo = otherPoint.subtract(dist);

                    if (validateAndAdd(antinode, grid, antennae)) antinodes.add(antinode);
                    if (validateAndAdd(antinodeTwo, grid, antennae)) antinodes.add(antinodeTwo);

                    if (resonantHarmonics) {
                        antinodes.add(point);
                        antinodes.add(otherPoint);

                        var nextAntinode = antinode;
                        while (true) {
                            nextAntinode = nextAntinode.add(dist);
                            if (!validateAndAdd(nextAntinode, grid, antennae)) break;
                            antinodes.add(nextAntinode);
                        }
                    }
                }
            }
        }

        for (var antinode : antinodes) {
            if (grid.get(antinode.x()).get(antinode.y()) == '.') grid.get(antinode.x()).set(antinode.y(), '#');
        }

        return antinodes;
    }

    private boolean validateAndAdd(Point antinode, List<List<Character>> map, Map<Character, List<Point>> antennae) {
        if (isInBounds(antinode.x(), antinode.y(), map)) {
            if (map.get(antinode.x()).get(antinode.y()) == '.') {
                return true;
            } else return antennae.containsKey(map.get(antinode.x()).get(antinode.y()));
        }
        return false;
    }
}
