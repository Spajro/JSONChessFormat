package chess.results;

import chess.moves.raw.RawMove;

public class InvalidMoveResult implements MoveResult {
    private final RawMove move;

    public InvalidMoveResult(RawMove move) {
        this.move = move;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public RawMove getMove() {
        return move;
    }
}
