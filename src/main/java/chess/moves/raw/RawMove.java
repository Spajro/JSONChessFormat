package chess.moves.raw;

import chess.Position;
import chess.moves.Vector;

public class RawMove extends Vector {
    public RawMove(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    public RawMove(Vector vector) {
        super(vector);
    }
}
