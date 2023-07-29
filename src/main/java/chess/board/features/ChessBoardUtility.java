package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.board.fields.Field;
import chess.pieces.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoardUtility {
    private final ChessBoard chessBoard;

    public ChessBoardUtility(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Map<Position, Long> getNumberOfPiecesAttackingFields(Color color) {
        Map<Position, Long> result = getPiecesOfColor(color).stream()
                .map(Piece::getAttackedPositions)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        getAllPositions().forEach(position -> result.merge(position, 0L, (v1, v2) -> v1));

        return result;
    }

    public List<Piece> getPiecesOfColor(Color color) {
        return getAllPositions().stream()
                .map(chessBoard::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.getColor().equal(color))
                .toList();
    }

    public List<Position> getAllPositions() {
        List<Position> result = new ArrayList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                result.add(Position.of(x, y));
            }
        }
        return result;
    }

    public boolean isPositionAttacked(Position position, Color attackingColor) {
        for (Position step : Steps.fullSteps) {
            if (isAttackedFromDirection(position, step, attackingColor)) {
                return true;
            }
        }
        for (Position step : Steps.knightSteps) {
            if (isAttackedByKnight(position, step, attackingColor)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAttackedFromDirection(Position position, Position direction, Color attackingColor) {
        Position temporaryPosition = position.add(direction);
        while (temporaryPosition.isOnBoard()) {
            Field field = chessBoard.getField(temporaryPosition);
            if (field.hasPiece()) {
                Piece piece = field.getPiece();
                if (!piece.getColor().equal(attackingColor)) {
                    return false;
                }
                return piece.getAttackedPositions().contains(position);
            }
            temporaryPosition = temporaryPosition.add(direction);
        }
        return false;
    }

    private boolean isAttackedByKnight(Position position, Position step, Color attackingColor) {
        Position temporaryPosition = position.add(step);
        if (!temporaryPosition.isOnBoard()) {
            return false;
        }
        Field field = chessBoard.getField(position.add(step));
        if (!field.hasPiece()) {
            return false;
        }
        Piece piece = field.getPiece();
        return piece instanceof Knight && piece.getColor().equal(attackingColor);
    }
}
