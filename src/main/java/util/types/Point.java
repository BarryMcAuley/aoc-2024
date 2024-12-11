package util.types;

public record Point(int x, int y) {
    public Point subtract(Point other) {
        int xDiff = this.x - other.x;
        int yDiff = this.y - other.y;
        return new Point(xDiff, yDiff);
    }

    public Point add(Point other) {
        int xDiff = this.x + other.x;
        int yDiff = this.y + other.y;
        return new Point(xDiff, yDiff);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
