package chess.results;

import chess.board.ChessBoard;
import chess.moves.ExecutableMove;
import chess.moves.UnTypedPromotion;
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
        return new ValidMoveResult(chessBoard.makeMove((ExecutableMove) promotion.type(type)), promotion.type(type));
    }
}
