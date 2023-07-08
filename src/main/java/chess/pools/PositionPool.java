package chess.pools;

import chess.Position;
import log.Log;

import java.util.ArrayList;
import java.util.List;

public class PositionPool {
    private static final PositionPool positionPool = new PositionPool();

    public static PositionPool getInstance() {
        return positionPool;
    }

    private final List<Position> positions = new ArrayList<>();

    private PositionPool() {
        for (int i =-2; i <= 10; i++) {
            for (int j = -2; j <= 10; j++) {
                positions.add(new Position(i, j));
            }
        }
    }

    public Position get(int x, int y) {
        if (-3<x && x<11 && -3<y && y<11) {
            return positions.get(x * 13 + y + 28);
        } else {
            return new Position(x, y);
        }
    }

    public List<Position> getAll() {
        return positions;
    }
}
