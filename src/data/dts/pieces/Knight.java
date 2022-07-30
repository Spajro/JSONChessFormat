package data.dts.pieces;

import data.dts.Position;
import data.dts.board.ChessBoard;
import data.dts.color.Color;
import data.dts.fields.Field;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece {
    public Knight(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Field::isEmpty)
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(field -> field.isEmpty() || !field.getPiece().getColor().equal(color))
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

}
