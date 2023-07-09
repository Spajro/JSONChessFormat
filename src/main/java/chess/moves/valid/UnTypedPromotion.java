package chess.moves.valid;

import chess.board.lowlevel.Board;
import chess.color.Color;
import chess.moves.valid.executable.Promotion;
import chess.moves.Vector;
import chess.moves.raw.RawMove;
import chess.pieces.Piece;

public class UnTypedPromotion extends Vector implements ValidMove {
    private final Board board;
    private final Color color;

    public UnTypedPromotion(RawMove move, Board board, Color color) {
        super(move);
        this.board = board;
        this.color = color;
    }

    @Override
    public RawMove getRepresentation() {
        return RawMove.of(this);
    }

    public Promotion type(Piece.Type type) {
        return new Promotion(this, board, color, type);
    }
}
