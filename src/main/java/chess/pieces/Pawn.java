package chess.pieces;

import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.Position;
import chess.color.Color;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pawn extends Piece {
    private enum Step {
        FRONT, LEFT, RIGHT, FAR
    }

    public Pawn(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        Set<Position> result = Stream.of(Step.FRONT, Step.FAR, Step.LEFT, Step.RIGHT)
                .map(this::getByStepBackward)
                .map(position::add)
                .filter(Position::isOnBoard)
                .filter(value -> !value.equals(position.add(getByStepBackward(Step.FAR))))
                .filter(value -> chessBoard.getField(value).isEmpty() || chessBoard.getField(value).getPiece().partiallyEquals(this))
                .collect(Collectors.toSet());
        if (isOnStartLine(position.add(getByStepBackward(Step.FAR)))
                && chessBoard.getField(position.add(getByStepBackward(Step.FRONT))).isEmpty()) {
            result.add(position.add(getByStepBackward(Step.FAR)));
        }
        return result;
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        Set<Position> result = new HashSet<>();
        if (isFrontEmpty()) {
            result.add(position.add(getByStepForward(Step.FRONT)));
        }
        if (canCaptureRight()) {
            result.add((position.add(getByStepForward(Step.RIGHT))));
        }
        if (canCaptureLeft()) {
            result.add((position.add(getByStepForward(Step.LEFT))));
        }
        if (isOnStartLine(position) && isFrontEmpty() && isFarEmpty()) {
            result.add(position.add(getByStepForward(Step.FAR)));
        }
        return result;
    }

    @Override
    public Set<Position> getAttackedPositions() {
        Set<Position> result = new HashSet<>();
        Position left = position.add(getByStepForward(Step.LEFT));
        Position right = position.add(getByStepForward(Step.RIGHT));
        if (left.isOnBoard()) {
            result.add(left);
        }
        if (right.isOnBoard()) {
            result.add(right);
        }
        return result;
    }

    @Override
    public Type getType() {
        return Type.PAWN;
    }

    private boolean isOnStartLine(Position temporaryPosition) {
        return (temporaryPosition.getY() == 2 && color.isWhite()) || (temporaryPosition.getY() == 7 && color.isBlack());
    }

    private boolean canCaptureLeft() {
        return isEnemyPieceOnStep(Step.LEFT);
    }

    private boolean canCaptureRight() {
        return isEnemyPieceOnStep(Step.RIGHT);
    }

    private boolean isEnemyPieceOnStep(Step step) {
        Position positionToCheck = position.add(getByStepForward(step));
        if (!positionToCheck.isOnBoard()) {
            return false;
        }
        Field field = chessBoard.getField(positionToCheck);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return !piece.getColor().equal(color);
    }

    private boolean isFrontEmpty() {
        return !chessBoard.getField(position.add(getByStepForward(Step.FRONT))).hasPiece();
    }

    private boolean isFarEmpty() {
        return !chessBoard.getField(position.add(getByStepForward(Step.FAR))).hasPiece();
    }


    private Position getByStepForward(Step step) {
        if (color.isWhite()) {
            return getByStepUp(step);
        } else {
            return getByStepDown(step);
        }
    }

    private Position getByStepBackward(Step step) {
        if (color.isWhite()) {
            return getByStepDown(step);
        } else {
            return getByStepUp(step);
        }
    }

    private Position getByStepUp(Step step) {
        return switch (step) {
            case FRONT -> Position.of(0, 1);
            case LEFT -> Position.of(-1, 1);
            case RIGHT -> Position.of(1, 1);
            case FAR -> Position.of(0, 2);
        };
    }

    private Position getByStepDown(Step step) {
        return switch (step) {
            case FRONT -> Position.of(0, -1);
            case LEFT -> Position.of(-1, -1);
            case RIGHT -> Position.of(1, -1);
            case FAR -> Position.of(0, -2);
        };
    }
}
