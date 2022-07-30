package src.data.dts.fields;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.pieces.Piece;

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
