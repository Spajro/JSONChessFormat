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
                .filter(possiblePosition -> {
                    var field = getField(position.add(possiblePosition));
                    return field.isPresent() && field.get().isEmpty();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return Steps.fullSteps.stream()
                .filter(possiblePosition -> {
                    var field = getField(position.add(possiblePosition));
                    return isLegal(field);
                })
                .collect(Collectors.toSet());
    }

    private boolean isLegal(Optional<Field> OptionalField) {
        if (OptionalField.isEmpty()) return false;
        Field field = OptionalField.get();
        Piece piece = field.getPiece();
        return (field.isEmpty() || piece.getColor().equal(color)) && field.isAttackedByColor(color.swap());
    }
}
