package chess.moves.raw;

import chess.Position;
import chess.moves.Vector;
import chess.pools.PoolManager;

public class RawMove extends Vector {
    public RawMove(Position startPosition, Position endPosition) {
        super(startPosition, endPosition);
    }

    protected RawMove(Vector vector) {
        super(vector);
    }

    public static RawMove of(Position startPosition, Position endPosition) {
        return PoolManager.getRawMovePool().get(startPosition, endPosition);
    }

    public static RawMove of(Vector vector) {
        return PoolManager.getRawMovePool().get(vector.getStartPosition(), vector.getEndPosition());
    }
}
