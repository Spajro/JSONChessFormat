package data.dts.pieces;

import data.dts.Position;
import data.dts.board.ChessBoard;
import data.dts.color.Color;
import data.dts.fields.Field;

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

    public Position getPosition() {
        return position;
    }

    protected Optional<Field> getField(Position position){
        if(position.isOnBoard()){
            return Optional.ofNullable(chessBoard.getField(position));
        }
        return Optional.empty();
    }
}
