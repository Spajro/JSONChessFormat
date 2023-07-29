package chess.board.fields;

import chess.Position;
import chess.pieces.Piece;

public class EmptyField implements Field {
    private final Position position;

    public EmptyField(Position position) {
        this.position = position;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasPiece() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
