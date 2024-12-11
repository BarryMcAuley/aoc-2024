package util.types;

import lombok.Getter;
import util.Parser;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Grid<T> {
    private final List<List<T>> grid = new ArrayList<>();

    public Grid(List<String> input, Parser<T> parser, String delimiter) {
        for (String line : input) {
            List<T> elements = new ArrayList<>();
            for (String value : line.split(delimiter)) {
                elements.add(parser.parse(value));
            }
            grid.add(elements);
        }
    }

    public List<T> getRow(int x) {
        return grid.get(x);
    }

    public T get(int x, int y) {
        return grid.get(x).get(y);
    }

    public int size() {
        return grid.size();
    }

    public List<Point> getAll(T value) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                if (grid.get(i).get(j).equals(value)) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<T> row : grid) {
            for (T element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
