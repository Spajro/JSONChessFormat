package src.data.dts.pieces;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;
import src.data.dts.fields.Field;

import java.util.Optional;
import java.util.Set;

public abstract class Piece extends Field {
    protected final Color color;


    Piece(Color color, Position position, ChessBoard chessBoard) {
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
