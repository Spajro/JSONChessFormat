package chess.board.fields;

import chess.Position;
import chess.pieces.Piece;

public class OccupiedField implements Field {
    private final Piece piece;

    public OccupiedField(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasPiece() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public Position getPosition() {
        return piece.getPosition();
    }
}
