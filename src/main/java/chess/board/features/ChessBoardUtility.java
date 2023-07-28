package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.board.lowlevel.Field;
import chess.pieces.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        int attackingPieces = Stream.of(
                        new Knight(attackingColor, position, chessBoard),
                        new Bishop(attackingColor, position, chessBoard),
                        new Rook(attackingColor, position, chessBoard),
                        new Queen(attackingColor, position, chessBoard),
                        new King(attackingColor, position, chessBoard))
                .map(Piece::getPossibleStartPositions)
                .mapToInt(Set::size)
                .sum();
        int attackingPawns = new Pawn(attackingColor, position, chessBoard)
                .getPossibleStartPositions()
                .stream()
                .filter(value -> position.getX() != value.getX())
                .collect(Collectors.toSet()).size();
        return attackingPieces + attackingPawns > 0;
    }
}
