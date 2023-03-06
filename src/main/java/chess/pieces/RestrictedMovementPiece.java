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

    protected Set<Position> getPossibleStartPositions(Set<Position> steps) {
        return getPossibleNonCollidingPositions(steps).stream()
                .filter(position -> chessBoard.getField(position).isEmpty())
                .collect(Collectors.toSet());
    }

    protected Set<Position> getPossibleEndPositions(Set<Position> steps) {
        return getPossibleNonCollidingPositions(steps).stream()
                .filter(position -> {
                    if (chessBoard.getField(position).isEmpty()) {
                        return true;
                    } else {
                        Piece piece = chessBoard.getField(position).getPiece();
                        return piece.color.equal(color.swap());
                    }
                })
                .collect(Collectors.toSet());
    }

    protected Set<Position> getAttackedPositions(Set<Position> steps) {
        return getPossibleNonCollidingPositions(steps);
    }

    private Set<Position> getPossibleNonCollidingPositions(Set<Position> Steps) {
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
            result.add(temporaryPosition);
            if (field.hasPiece()) {
                break;
            }
            temporaryPosition = temporaryPosition.add(step);
        }
        return result;
    }
}
