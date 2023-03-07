package chess;

public class Position {
    protected final int x;
    protected final int y;

    public Position(int nx, int ny) {
        x = nx;
        y = ny;
    }

    public Position(Position p) {
        x = p.x;
        y = p.y;
    }

    public Position add(Position p) {
        return new Position(x + p.x, y + p.y);
    }

    public Position subtract(Position p) {
        return new Position(x - p.x, y - p.y);
    }

    public boolean isBetween(Position first, Position second) {
        if (first.x == second.x) {
            return x == first.x && isBetween(first.y, y, second.y);
        }
        if (first.y == second.y) {
            return y == first.y && isBetween(first.x, x, second.x);
        } else {
            Position diff = first.subtract(second);
            return Math.abs(diff.x) == Math.abs(diff.y)
                    && isBetween(first.y, y, second.y)
                    && isBetween(first.x, x, second.x);
        }
    }

    private boolean isBetween(int first, int middle, int second) {
        return (first < middle && middle < second) || (first > middle && middle > second);
    }

    public boolean isOnBoard() {
        return !(x < 1 || y < 1 || x > 8 || y > 8);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + ">";
    }
}
