package util;

import util.types.Grid;

public class Util {

    public static <T> boolean isInBounds(int x, int y, Grid<T> grid) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.get(0).size();
    }
}
