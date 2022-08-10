package chess.pieces;

import chess.board.ChessBoard;
import chess.fields.Field;
import chess.Position;
import chess.color.Color;

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
                .peek(set-> System.err.print(set.size()+"A\n"))
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
            temporaryPosition=temporaryPosition.add(step);
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
