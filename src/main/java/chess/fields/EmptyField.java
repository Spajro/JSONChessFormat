package chess.fields;

import chess.board.ChessBoard;
import chess.Position;
import chess.pieces.Piece;

public class EmptyField extends Field{
    public EmptyField(Position position, ChessBoard chessBoard) {
        super(position, chessBoard);
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
}
