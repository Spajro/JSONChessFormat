package data.dts.fields;

import data.dts.Position;
import data.dts.board.ChessBoard;
import data.dts.pieces.Piece;

public class EmptyField extends Field{
    public EmptyField(Position position, ChessBoard chessBoard) {
        super(position, chessBoard);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
