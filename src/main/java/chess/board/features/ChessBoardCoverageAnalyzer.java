package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardCoverageAnalyzer {
    private final ChessBoard chessBoard;

    public enum Coverage {
        STRONG,
        NEUTRAL,
        WEAK,
    }

    public ChessBoardCoverageAnalyzer(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Map<Position, Coverage> getBoardCoverage(Color color) {
        Map<Position, Long> colorAttacking = chessBoard.getUtility().getNumberOfPiecesAttackingFields(color);
        Map<Position, Long> oppositeColorAttacking = chessBoard.getUtility().getNumberOfPiecesAttackingFields(color.swap());
        Map<Position, Coverage> result = new HashMap<>();
        chessBoard.getUtility().getAllPositions().forEach(position -> {
            long difference = colorAttacking.get(position) - oppositeColorAttacking.get(position);
            Coverage temp;
            if (difference > 0) {
                temp = Coverage.STRONG;
            } else if (difference < 0) {
                temp = Coverage.WEAK;
            } else {
                temp = Coverage.NEUTRAL;
            }
            result.put(position, temp);
        });
        return result;
    }
}
