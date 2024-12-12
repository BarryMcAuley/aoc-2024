package org.example;

import util.FileUtil;
import util.types.Direction;
import util.types.Grid;
import util.types.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Stack;

public class DayTen {

    public static void main(String[] args) {
        System.out.println("Day 10");
        var aoc = new DayTen();
        aoc.navigate();
    }

    public void navigate() {
        try {
            var map = FileUtil.readGrid("src/main/resources/dayten.txt", Integer::parseInt, "");
            var trailheads = map.getAll(0);

            var nines = 0;
            var trails = 0;
            for (var th : trailheads) {
                nines += DFS(map, th, true);
                trails += DFS(map, th, false);
            }

            System.out.println("Nines: " + nines);
            System.out.println("Trails: " + trails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int DFS(Grid<Integer> map, Point start, boolean trackVisited) {
        var count = 0;
        var stack = new Stack<Point>();
        var visited = trackVisited ? new HashSet<Point>() : null;
        stack.add(start);

        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (map.get(current.x(), current.y()) == 9 && (visited == null || !visited.contains(current))) {
                count++;
            }

            if (visited != null) {
                if (visited.contains(current)) {
                    continue;
                }
                visited.add(current);
            }

            for (var dir : Direction.values()) {
                var neighbour = new Point(current.x() + dir.dx(), current.y() + dir.dy());
                if (map.isInBounds(neighbour.x(), neighbour.y())
                        && map.get(neighbour.x(), neighbour.y()) - map.get(current.x(), current.y()) == 1)
                    stack.add(neighbour);
            }
        }

        return count;
    }
}