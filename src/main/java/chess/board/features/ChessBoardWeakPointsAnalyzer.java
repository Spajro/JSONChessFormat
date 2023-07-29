package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.board.fields.Field;
import chess.pieces.Piece;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessBoardWeakPointsAnalyzer {
    private final ChessBoard board;

    public ChessBoardWeakPointsAnalyzer(ChessBoard board) {
        this.board = board;
    }

    public Set<Position> getWeakPoints(Color color) {
        return board.getUtility()
                .getNumberOfPiecesAttackingFields(color)
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 0)
                .map(Map.Entry::getKey)
                .map(board::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.getColor().equal(color))
                .map(Piece::getPosition)
                .collect(Collectors.toSet());
    }
}
