package org.example;

import util.FileUtil;
import util.types.Direction;
import util.types.Grid;
import util.types.Point;

import java.io.IOException;
import java.util.*;

public class DayTwelve {
    public static void main(String[] args) {
        System.out.println("Day 12");
        var dayTwelve = new DayTwelve();
        dayTwelve.fenceGarden();
    }

    public void fenceGarden() {
        System.out.println("Fence the garden");
        try {
            var garden = FileUtil.readGrid("src/main/resources/daytwelve.txt", s -> s.charAt(0), "");
            var regions = countRegions(garden, false);
            var totalCost = regions.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println(totalCost);
            var discountCount = countRegions(garden, true).values().stream().mapToInt(Integer::intValue).sum();
            System.out.println(discountCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Set<Point>, Integer> countRegions(Grid<Character> garden, boolean countSides) {
        var stack = new Stack<Point>();
        var visited = new boolean[garden.size()][garden.get(0).size()];
        var regions = new HashMap<Set<Point>, Integer>();

        for (int x = 0; x < garden.size(); x++) {
            for (int y = 0; y < garden.get(x).size(); y++) {
                if (visited[x][y]) {
                    continue;
                }

                var region = new HashSet<Point>();
                visited[x][y] = true;
                stack.push(new Point(x, y));

                while (!stack.isEmpty()) {
                    var current = stack.pop();
                    region.add(current);

                    for (var dir : Direction.values()) {
                        var neighbour = new Point(current.x() + dir.dx(), current.y() + dir.dy());
                        if (!garden.isInBounds(neighbour.x(), neighbour.y())) {
                            continue;
                        }

                        var isSameRegion = garden.get(neighbour.x(), neighbour.y()) == garden.get(current.x(), current.y());
                        if (isSameRegion && !visited[neighbour.x()][neighbour.y()]) {
                            visited[neighbour.x()][neighbour.y()] = true;
                            stack.push(neighbour);
                        }
                    }
                }

                if (countSides) {
                    var sides = countRegionSides(region, garden);
                    System.out.println("Region: " + region + " Sides: " + sides);
                    System.out.println("Cost: " + (sides * region.size()));
                    regions.putIfAbsent(region, sides * region.size());
                } else {
                    var fences = countFences(garden, region);
                    regions.putIfAbsent(region, fences * region.size());
                }
            }
        }

        return regions;
    }

    private static int countFences(Grid<Character> garden, Set<Point> region) {
        var fences = 0;
        for (Point p : region) {
            for (var dir : Direction.values()) {
                var neighbour = new Point(p.x() + dir.dx(), p.y() + dir.dy());
                if (!garden.isInBounds(neighbour.x(), neighbour.y()) ||
                        garden.get(neighbour.x(), neighbour.y()) != garden.get(p.x(), p.y())) {
                    fences++;
                }
            }
        }
        return fences;
    }

    private int countRegionSides(Set<Point> region, Grid<Character> garden) {
        if (region.isEmpty()) {
            return 0;
        }

        Point firstPoint = region.iterator().next();
        Character regionLetter = garden.get(firstPoint.x(), firstPoint.y());
        int edges = 0;

        for (Point p : region) {
            int x = p.x();
            int y = p.y();

            for (Direction direction : Direction.values()) {
                // Check if neighbor is within bounds
                if (!garden.isInBounds(x + direction.dx(), y + direction.dy())) {
                    edges++;
                } else {
                    Character neighborChar = garden.get(x + direction.dx(), y + direction.dy());
                    if (!regionLetter.equals(neighborChar)) {
                        edges++;
                    }
                }
            }
        }

        return edges;
    }
}
