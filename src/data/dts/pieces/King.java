package data.dts.pieces;

import data.dts.Position;
import data.dts.board.ChessBoard;
import data.dts.color.Color;
import data.dts.fields.Field;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {
    public King(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return Steps.fullSteps.stream()
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
        return Steps.fullSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(this::isLegal)
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    private boolean isLegal(Field field) {
        if (field.isEmpty()) {
            return true;
        }
        Piece piece = field.getPiece();
        return (field.isEmpty() || piece.getColor().equal(color)) && field.isAttackedByColor(color.swap());
    }
}
