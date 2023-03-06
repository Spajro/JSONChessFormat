package chess.fields;

import chess.board.ChessBoard;
import chess.Position;
import chess.pieces.*;

public abstract class Field {
    protected final Position position;
    protected final ChessBoard chessBoard;

    public Field(Position position, ChessBoard chessBoard) {
        this.position = position;
        this.chessBoard = chessBoard;
    }

    public abstract boolean isEmpty();

    public abstract boolean hasPiece();

    public abstract Piece getPiece();

    public Position getPosition() {
        return position;
    }
}
