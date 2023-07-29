package chess.pieces;

import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.Position;
import chess.color.Color;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class RestrictedMovementPiece extends Piece {
    RestrictedMovementPiece(Color color, Position position) {
        super(color, position);
    }

    protected Set<Position> getPossibleStartPositions(ChessBoard chessBoard, Set<Position> steps) {
        return getPossibleNonCollidingPositions(chessBoard, steps).stream()
                .map(p -> getField(chessBoard, p))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.partiallyEquals(this))
                .map(Piece::getPosition)
                .collect(Collectors.toSet());
    }

    protected Set<Position> getPossibleEndPositions(ChessBoard chessBoard, Set<Position> steps) {
        return getPossibleNonCollidingPositions(chessBoard, steps).stream()
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

    protected Set<Position> getAttackedPositions(ChessBoard chessBoard, Set<Position> steps) {
        return getPossibleNonCollidingPositions(chessBoard, steps);
    }

    private Set<Position> getPossibleNonCollidingPositions(ChessBoard chessBoard, Set<Position> Steps) {
        return Steps.stream()
                .map(p -> getLineOfPossibleNonCollidingPositions(chessBoard, p))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Position> getLineOfPossibleNonCollidingPositions(ChessBoard chessBoard, Position step) {
        Set<Position> result = new HashSet<>();
        Position temporaryPosition = position.add(step);
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
