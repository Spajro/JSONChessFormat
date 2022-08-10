package chess.fields;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;
import chess.pieces.Piece;

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

    public boolean isAttackedByColor(Color color){
        return chessBoard.getNumberOfPiecesAttackingFields(color).get(position)>0;
    }

    public Position getPosition() {
        return position;
    }
}
