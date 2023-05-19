package chess.utility;

import chess.Position;
import chess.color.Color;
import chess.moves.RawMove;

import java.util.Optional;

public class LongAlgebraicParser {
    private final AlgebraicUtility utility = AlgebraicUtility.getInstance();

    public RawMove parseLongAlgebraic(String move, Color color) {
        return utility.algebraicCastleToMove(move, color)
                .orElseGet(() -> longAlgebraicToMove(move)
                        .orElseThrow(() -> new IllegalArgumentException("Wrong algebraic"))
                );
    }


    private Optional<RawMove> longAlgebraicToMove(String move) {
        String rawMove = slicePieceId(move);
        Optional<Position> start = utility.algebraicToPosition(rawMove.substring(0, 2));
        Optional<Position> end = utility.algebraicToPosition(rawMove.substring(3));
        if (start.isEmpty() || end.isEmpty()) {
            return Optional.empty();
        }
        if (rawMove.charAt(2) == '-') {
            return Optional.of(new RawMove(
                    start.get(),
                    end.get()));
        }
        return Optional.empty();
    }


    private String slicePieceId(String move) {
        if (move.length() == 6) {
            return move.substring(1);
        }
        return move;
    }
}
