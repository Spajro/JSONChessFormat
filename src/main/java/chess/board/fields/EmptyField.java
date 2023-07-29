package chess.board.fields;

import chess.Position;
import chess.pieces.Piece;
import chess.pools.PoolManager;

import java.util.Objects;

public class EmptyField implements Field {
    private final Position position;

    public EmptyField(Position position) {
        this.position = position;
    }

    public static EmptyField of(Position position) {
        return PoolManager.getEmptyFieldPool().get(position);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmptyField that = (EmptyField) o;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
