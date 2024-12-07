package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Direction {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public Direction turnRight() {
        return values()[(ordinal() + 1) % 4];
    }
}

class Point {
    int x, y;
    Direction direction;

    Point(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y && direction == other.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }
}

public class DaySix {
    private final List<List<Character>> grid = new ArrayList<>();
    private final Queue<Point> toAddObstacle = new LinkedList<>();
    private int startX;
    private int startY;


    public void loadGrid() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/daysix.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String finalLine = line;
            grid.add(IntStream.range(0, line.length())
                    .mapToObj(i -> {
                        char c = finalLine.charAt(i);
                        if (c == '^') {
                            startX = grid.size();
                            startY = i;
                        }
                        return c;
                    })
                    .collect(Collectors.toCollection(ArrayList::new)));  // This makes it mutable
        }
    }

    private void printGrid(List<ArrayList<Character>> grid) {
        System.out.println("Grid:");
        grid.forEach(row -> System.out.println(
                row.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining())
        ));
    }

    private boolean isNotFinished(int x, int y) {
        return x != 0 && x != grid.size() - 1 && y != 0 && y != grid.getFirst().size() - 1;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.getFirst().size();
    }

    public void partOne() {
        var grid = this.grid.stream().map(ArrayList::new).toList();
        var output = 0;
        var currDirection = Direction.NORTH;
        var x = startX;
        var y = startY;

        while (isNotFinished(x, y)) {
            if (grid.get(x + currDirection.dx()).get(y + currDirection.dy()) == '#') {
                currDirection = currDirection.turnRight();
                continue;
            }

            if (grid.get(x).get(y) != 'X') {
                output++;
                grid.get(x).set(y, 'X');
                toAddObstacle.add(new Point(x, y, currDirection));
            }

            x += currDirection.dx();
            y += currDirection.dy();
        }
        grid.get(x).set(y, 'X');
        toAddObstacle.add(new Point(x, y, currDirection));
        output++;

        System.out.println(output);
    }

    private boolean hasLoop(int x, int y, List<List<Character>> grid) {
        var slowX = x;
        var slowY = y;
        var slowDirection = Direction.NORTH;

        var fastX = x;
        var fastY = y;
        var fastDirection = Direction.NORTH;

        while (isNotFinished(slowX, slowY) && isNotFinished(fastX, fastY)) {
            var slowMove = move(slowX, slowY, slowDirection, grid);
            slowX = slowMove[0];
            slowY = slowMove[1];
            slowDirection = Direction.values()[slowMove[2]];

            for (int i = 0; i < 2; i++) {
                var fastMove = move(fastX, fastY, fastDirection, grid);
                fastX = fastMove[0];
                fastY = fastMove[1];
                fastDirection = Direction.values()[fastMove[2]];
            }

            if (slowX == fastX && slowY == fastY && slowDirection == fastDirection) {
                return true;
            }

        }

        return false;
    }

    private int[] move(int x, int y, Direction direction, List<List<Character>> grid) {
        var nextX = x + direction.dx();
        var nextY = y + direction.dy();

        if (!isInBounds(nextX, nextY) || grid.get(nextX).get(nextY) == '#') {
            direction = direction.turnRight();
            return new int[]{x, y, direction.ordinal()};
        }

        return new int[]{nextX, nextY, direction.ordinal()};
    }


    public void partTwo() {
        var output = 0;

        toAddObstacle.poll(); // discard the starting point
        while (!toAddObstacle.isEmpty()) {
            var point = toAddObstacle.poll();
            var original = grid.get(point.x).get(point.y);
            grid.get(point.x).set(point.y, '#');

            if (hasLoop(startX, startY, grid)) {
                output++;
            }

            grid.get(point.x).set(point.y, original);
        }

        System.out.println(output);
    }

    public static void main(String[] args) {
        System.out.println("Day Six");

        try {
            DaySix daySix = new DaySix();
            daySix.loadGrid();
            daySix.partOne();
            daySix.partTwo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
