package chess.results;

import chess.board.ChessBoard;

public class ValidMoveResult implements MoveResult {
    private final ChessBoard result;

    public ValidMoveResult(ChessBoard result) {
        this.result = result;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public ChessBoard getResult() {
        return result;
    }
}
