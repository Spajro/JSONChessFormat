package chess.moves;

import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.color.Color;
import chess.pieces.Piece;

public class UnTypedPromotion extends RawMove implements ValidMove {
    private final Board board;
    private final Color color;

    public UnTypedPromotion(RawMove move, Board board, Color color) {
        super(move);
        this.board = board;
        this.color = color;
    }

    @Override
    public RawMove getRepresentation() {
        return this;
    }

    public Promotion type(Piece.Type type) {
        return new Promotion(this, board, color, type);
    }
}
