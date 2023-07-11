package chess;

import chess.pools.PoolManager;

import static java.lang.Math.*;

public class Position {
    protected final int x;
    protected final int y;

    public Position(int nx, int ny) {
        x = nx;
        y = ny;
    }

    public static Position of(int nx, int ny) {
        return PoolManager.getPositionPool().get(nx, ny);
    }

    public Position add(Position p) {
        return of(x + p.x, y + p.y);
    }

    public Position subtract(Position p) {
        return of(x - p.x, y - p.y);
    }

    public boolean isBetween(Position first, Position second) {
        if (first.x == second.x) {
            return x == first.x && isBetween(first.y, y, second.y);
        }
        if (first.y == second.y) {
            return y == first.y && isBetween(first.x, x, second.x);
        } else {
            Position diff = first.subtract(second);
            if (abs(diff.x) == abs(diff.y)) {
                Position direction = of(diff.x / abs(diff.x), diff.y / abs(diff.y));
                Position temp = second;
                while (!temp.equals(first)) {
                    if (temp.equals(this)) {
                        return true;
                    } else {
                        temp = temp.add(direction);
                    }
                }
            }
            return false;
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
