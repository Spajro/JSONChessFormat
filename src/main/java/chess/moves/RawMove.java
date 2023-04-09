package chess.moves;

import chess.Position;

import java.util.Objects;

public class RawMove {
    private final Position startPosition;
    private final Position endPosition;

    public RawMove(Position startPosition, Position endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public RawMove(RawMove move) {
        this.startPosition = move.getStartPosition();
        this.endPosition = move.getEndPosition();
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
        RawMove rawMove = (RawMove) o;
        return startPosition.equals(rawMove.startPosition) && endPosition.equals(rawMove.endPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition);
    }
}
