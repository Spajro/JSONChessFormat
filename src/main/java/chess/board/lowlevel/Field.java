package chess.board.lowlevel;

import chess.Position;
import chess.pieces.*;

public class Field {
    private final Position position;
    private final Piece piece;

    public Field(Position position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }
}
