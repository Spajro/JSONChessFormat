package src.data.dts.pieces;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;

import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece{
    Knight(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return Steps.knightSteps.stream()
                .filter(possiblePosition -> {
                    var field=getField(position.add(possiblePosition));
                    return field.isPresent() && field.get().isEmpty();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return Steps.knightSteps.stream()
                .filter(possiblePosition -> {
                    var field=getField(position.add(possiblePosition));
                    return field.isEmpty() || !field.get().getPiece().getColor().equal(color);
                })
                .collect(Collectors.toSet());
    }

}
