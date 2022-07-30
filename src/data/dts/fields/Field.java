package src.data.dts.fields;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;
import src.data.dts.pieces.Piece;

public abstract class Field {
    protected final Position position;
    protected final ChessBoard chessBoard;

    public Field(Position position, ChessBoard chessBoard) {
        this.position = position;
        this.chessBoard = chessBoard;
    }

    public abstract boolean isEmpty();
    public abstract Piece getPiece();

    public boolean isAttackedByColor(Color color){
        //TODO
        return false;
    }
}
