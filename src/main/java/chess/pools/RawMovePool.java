package chess.pools;

import chess.Position;
import chess.moves.raw.RawMove;

import java.util.ArrayList;

public class RawMovePool {
    private final ArrayList<RawMove> pool = new ArrayList<>();

    RawMovePool() {
        for (int a = 1; a < 9; a++) {
            for (int b = 1; b < 9; b++) {
                for (int c = 1; c < 9; c++) {
                    for (int d = 1; d < 9; d++) {
                        pool.add(new RawMove(Position.of(a, b), Position.of(c, d)));
                    }
                }
            }
        }
    }

    public RawMove get(Position p1, Position p2) {
        if (p1.isOnBoard() && p2.isOnBoard()) {
            return pool.get(p1.getX() * 8 * 8 * 8 + p1.getY() * 8 * 8 + p2.getX() * 8 + p2.getY()-585);
        }
        return RawMove.of(p1, p2);
    }

}
