package src.data.dts.pieces;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;
import src.data.dts.fields.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RestrictedMovementPiece extends Piece {
    RestrictedMovementPiece(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    protected Set<Position> getPossibleNonCollidingPositions(Set<Position> Steps) {
        return Steps.stream()
                .map(this::getLineOfPossibleNonCollidingPositions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Position> getLineOfPossibleNonCollidingPositions(Position step) {
        Set<Position> result = new HashSet<>();
        Position temporaryPosition = new Position(position).add(step);
        while (temporaryPosition.isOnBoard()) {
            Field field = chessBoard.getField(temporaryPosition);
            if (field.isEmpty()) {
                result.add(temporaryPosition);
            } else {
                Piece piece = field.getPiece();
                if (!piece.color.equal(this.color)) {
                    result.add(temporaryPosition);
                }
                break;
            }
        }
        return result;
    }

    protected Set<Position> getPossibleStartPositions(Set<Position> steps) {
        return getPossibleNonCollidingPositions(steps).stream()
                .filter(position -> chessBoard.getField(position).isEmpty())
                .collect(Collectors.toSet());
    }

    protected Set<Position> getPossibleEndPositions(Set<Position> steps) {
        return getPossibleNonCollidingPositions(steps);
    }

}
