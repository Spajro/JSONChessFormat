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

    public abstract Set<Position> getPossibleStartPositions();

    public abstract Set<Position> getPossibleEndPositions();

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

    protected Optional<Field> getField(Position position){
        if(position.isOnBoard()){
            return Optional.ofNullable(chessBoard.getField(position));
        }
        return Optional.empty();
    }
}
