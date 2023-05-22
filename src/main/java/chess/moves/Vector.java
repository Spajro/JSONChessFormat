package chess.moves;

import chess.Position;

import java.util.Objects;

public abstract class Vector {
    private final Position startPosition;
    private final Position endPosition;

    public Vector(Position startPosition, Position endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Vector(Vector vector) {
        this.startPosition = vector.getStartPosition();
        this.endPosition = vector.getEndPosition();
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    @Override
    public String toString() {
        return "<" + startPosition + "," + endPosition + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return startPosition.equals(vector.startPosition) && endPosition.equals(vector.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition);
    }
}
