package chess.results;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;

public class ValidMoveResult implements MoveResult {
    private final ChessBoard result;
    private final ExecutableMove move;

    public ValidMoveResult(ChessBoard result, ExecutableMove move) {
        this.result = result;
        this.move = move;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public ChessBoard getResult() {
        return result;
    }

    @Override
    public RawMove getMove() {
        return move.getRepresentation();
    }

    public ExecutableMove getExecutableMove() {
        return move;
    }
}
