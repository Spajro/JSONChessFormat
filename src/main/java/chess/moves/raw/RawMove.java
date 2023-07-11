package chess.moves.raw;

import chess.Position;
import chess.moves.Vector;

public class RawMove extends Vector {
    RawMove(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    protected RawMove(Vector vector) {
        super(vector);
    }

    public static RawMove of(Position startPosition, Position endPosition) {
        return new RawMove(startPosition, endPosition);
    }
    public static RawMove of(Vector vector) {
        return new RawMove(vector);
    }
}
