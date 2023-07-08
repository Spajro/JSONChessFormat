package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.board.lowlevel.Field;
import chess.pieces.Piece;
import chess.pools.PositionPool;

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
        List<Position> result = new LinkedList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                result.add(Position.of(x, y));
            }
        }
        return result;
    }

    public boolean isPositionAttacked(Position position, Color attackingColor) {
        return getNumberOfPiecesAttackingFields(attackingColor).get(position) > 0;
    }

    public Set<Piece> getPiecesAttackingPosition(Position kingPosition, Color attackingColor) {
        return getPiecesOfColor(attackingColor).stream()
                .filter(piece -> piece.getPossibleEndPositions().contains(kingPosition))
                .collect(Collectors.toSet());
    }
}
