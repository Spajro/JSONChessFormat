package chess.pools;

public class PoolManager {
    private static final PositionPool positionPool;
    private static final RawMovePool rawMovePool;

    static {
        positionPool = new PositionPool();
        rawMovePool = new RawMovePool();
    }

    public static PositionPool getPositionPool() {
        return positionPool;
    }

    public static RawMovePool getRawMovePool() {
        return rawMovePool;
    }
}
