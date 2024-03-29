package chess.pieces;

import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.Position;
import chess.color.Color;

import java.util.Optional;
import java.util.Set;

public abstract class Piece {
    protected final Position position;
    protected final Color color;

    public enum Type {
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING,
    }

    public Piece(Color color, Position position) {
        this.position = position;
        this.color = color;
    }

    /**
     * @return set of positions from which piece could move to current position (fields with this piece)
     */
    public abstract Set<Position> getPossibleStartPositions(ChessBoard chessBoard);

    /**
     * @return set of positions to which piece can move from current position (empty fields and fields with enemy pieces)
     */
    public abstract Set<Position> getPossibleEndPositions(ChessBoard chessBoard);

    /**
     * @return set of positions that are attacked by piece ( empty fields and fields with pieces)
     */
    public abstract Set<Position> getAttackedPositions(ChessBoard chessBoard);

    public Color getColor() {
        return color;
    }

    protected Optional<Field> getField(ChessBoard chessBoard,Position position) {
        if (position.isOnBoard()) {
            return Optional.ofNullable(chessBoard.getField(position));
        }
        return Optional.empty();
    }

    public Position getPosition() {
        return position;
    }

    public abstract Type getType();

    public boolean partiallyEquals(Piece piece) {
        return this.getType().equals(piece.getType())
                && this.getColor().equal(piece.getColor());
    }
}
