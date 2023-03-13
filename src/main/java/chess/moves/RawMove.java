package chess.moves;

import chess.Position;

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
}
