package org.example;

import util.FileUtil;
import util.types.Grid;
import util.types.Point;

import java.io.IOException;
import java.util.*;

import static util.Util.isInBounds;

public class DayEight {

    public static void main(String[] args) {
        System.out.println("Day Eight");
        var dayEight = new DayEight();
        dayEight.partOne();
        dayEight.partTwo();
    }

    private Map<Character, List<Point>> loadAntennae(Grid<Character> grid) {
        var antennae = new HashMap<Character, List<Point>>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getRow(i).size(); j++) {
                if (grid.getRow(i).get(j) != '.') {
                    antennae.putIfAbsent(grid.getRow(i).get(j), new ArrayList<>());
                    antennae.get(grid.getRow(i).get(j)).add(new Point(i, j));
                }
            }
        }
        return antennae;
    }

    public void partOne() {
        System.out.println("Part One");
        try {
            var grid = FileUtil.readGrid("src/main/resources/dayeight.txt", s -> s.charAt(0), "");
            var antennae = loadAntennae(grid);
            var antinodes = getAntinodes(antennae, grid, false);
            System.out.println(antinodes.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partTwo() {
        System.out.println("Part Two");
        try {
            var grid = FileUtil.readGrid("src/main/resources/dayeight.txt", s -> s.charAt(0), "");
            var antennae = loadAntennae(grid);
            var antinodes = getAntinodes(antennae, grid, true);
            System.out.println(antinodes.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HashSet<Point> getAntinodes(Map<Character, List<Point>> antennae, Grid<Character> grid, boolean resonantHarmonics) {
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
            if (grid.getRow(antinode.x()).get(antinode.y()) == '.') grid.getRow(antinode.x()).set(antinode.y(), '#');
        }

        return antinodes;
    }

    private boolean validateAndAdd(Point antinode, Grid<Character> grid, Map<Character, List<Point>> antennae) {
        if (isInBounds(antinode.x(), antinode.y(), grid)) {
            if (grid.getRow(antinode.x()).get(antinode.y()) == '.') {
                return true;
            } else return antennae.containsKey(grid.getRow(antinode.x()).get(antinode.y()));
        }
        return false;
    }
}
