package chess.moves;

import chess.board.ChessBoard;
import chess.pieces.Piece;

public class UnTypedPromotion extends RawMove implements ValidMove {
    private final ChessBoard chessBoard;

    public UnTypedPromotion(RawMove move, ChessBoard chessBoard) {
        super(move);
        this.chessBoard = chessBoard;
    }

    @Override
    public RawMove getRepresentation() {
        return this;
    }

    public Promotion type(Piece.Type type) {
        return new Promotion(this, chessBoard, type);
    }
}
