package chess.pools;

public class PoolManager {
    private static final PositionPool positionPool;
    private static final RawMovePool rawMovePool;
    private static final EmptyFieldPool emptyFieldPool;
    private static final PiecePool piecePool;

    static {
        positionPool = new PositionPool();
        rawMovePool = new RawMovePool();
        emptyFieldPool = new EmptyFieldPool();
        piecePool = new PiecePool();
    }

    public static PositionPool getPositionPool() {
        return positionPool;
    }

    public static RawMovePool getRawMovePool() {
        return rawMovePool;
    }

    public static EmptyFieldPool getEmptyFieldPool() {
        return emptyFieldPool;
    }

    public static PiecePool getPiecePool() {
        return piecePool;
    }
}
