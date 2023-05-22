package chess.results;

import chess.board.ChessBoard;
import chess.moves.valid.executable.ExecutableMove;
import chess.moves.valid.UnTypedPromotion;
import chess.pieces.Piece;

public class PromotionResult implements MoveResult {
    private final ChessBoard chessBoard;
    private final UnTypedPromotion promotion;

    public PromotionResult(ChessBoard chessBoard, UnTypedPromotion promotion) {
        this.chessBoard = chessBoard;
        this.promotion = promotion;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public ValidMoveResult type(Piece.Type type) {
        return new ValidMoveResult(chessBoard.makeMove(promotion.type(type)), promotion.type(type));
    }
}
