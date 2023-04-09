package chess.pieces;

import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.Position;
import chess.color.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Pawn extends Piece {
    private enum Step {
        FRONT, LEFT, RIGHT, FAR
    }

    private final HashMap<Step, Position> stepsForward = new HashMap<>();
    private final HashMap<Step, Position> stepsBackward = new HashMap<>();

    public Pawn(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
        if (color.isWhite()) {
            populateWhite(stepsForward);
            populateBlack(stepsBackward);
        }
        if (color.isBlack()) {
            populateWhite(stepsBackward);
            populateBlack(stepsForward);
        }
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        Set<Position> result = stepsBackward.values().stream()
                .map(position::add)
                .filter(value -> !value.equals(position.add(stepsBackward.get(Step.FAR))))
                .filter(value -> chessBoard.getField(value).isEmpty() || chessBoard.getField(value).getPiece().partiallyEquals(this))
                .collect(Collectors.toSet());
        if (isOnStartLine(position.add(stepsBackward.get(Step.FAR)))
                && chessBoard.getField(position.add(stepsBackward.get(Step.FRONT))).isEmpty()) {
            result.add(position.add(stepsBackward.get(Step.FAR)));
        }
        return result;
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        Set<Position> result = new HashSet<>();
        if (isFrontEmpty()) {
            result.add(position.add(stepsForward.get(Step.FRONT)));
        }
        if (canCaptureRight()) {
            result.add((position.add(stepsForward.get(Step.RIGHT))));
        }
        if (canCaptureLeft()) {
            result.add((position.add(stepsForward.get(Step.LEFT))));
        }
        if (isOnStartLine(position) && isFrontEmpty() && isFarEmpty()) {
            result.add(position.add(stepsForward.get(Step.FAR)));
        }
        return result;
    }

    @Override
    public Set<Position> getAttackedPositions() {
        Set<Position> result = new HashSet<>();
        Position left = position.add(stepsForward.get(Step.LEFT));
        Position right = position.add(stepsForward.get(Step.RIGHT));
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
        Position positionToCheck = position.add(stepsForward.get(step));
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
        return !chessBoard.getField(position.add(stepsForward.get(Step.FRONT))).hasPiece();
    }

    private boolean isFarEmpty() {
        return !chessBoard.getField(position.add(stepsForward.get(Step.FAR))).hasPiece();
    }

    private void populateWhite(HashMap<Step, Position> map) {
        map.put(Step.FRONT, new Position(0, 1));
        map.put(Step.LEFT, new Position(-1, 1));
        map.put(Step.RIGHT, new Position(1, 1));
        map.put(Step.FAR, new Position(0, 2));
    }

    private void populateBlack(HashMap<Step, Position> map) {
        map.put(Step.FRONT, new Position(0, -1));
        map.put(Step.LEFT, new Position(-1, -1));
        map.put(Step.RIGHT, new Position(1, -1));
        map.put(Step.FAR, new Position(0, -2));
    }
}
