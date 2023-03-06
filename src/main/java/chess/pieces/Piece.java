package chess.pieces;

import chess.board.ChessBoard;
import chess.fields.Field;
import chess.Position;
import chess.color.Color;

import java.util.Optional;
import java.util.Set;

public abstract class Piece extends Field {
    protected final Color color;

    public Piece(Color color, Position position, ChessBoard chessBoard) {
        super(position, chessBoard);
        this.color = color;
    }

    /**
     * @return set of positions from which piece could move to current position (only empty fields)
     */
    public abstract Set<Position> getPossibleStartPositions();

    /**
     * @return set of positions to which piece can move from current position (empty fields and fields with enemy pieces)
     */
    public abstract Set<Position> getPossibleEndPositions();

    /**
     * @return set of positions that are attacked by piece ( empty fields and fields with pieces)
     */
    public abstract Set<Position> getAttackedPositions();

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
        return this;
    }

    public Color getColor() {
        return color;
    }

    protected Optional<Field> getField(Position position) {
        if (position.isOnBoard()) {
            return Optional.ofNullable(chessBoard.getField(position));
        }
        return Optional.empty();
    }
}
