package chess.utility;

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
        if (rawMove.charAt(2) == '-') {
            return Optional.of(new RawMove(
                    utility.algebraicToPosition(rawMove.substring(0, 2)),
                    utility.algebraicToPosition(rawMove.substring(3))));
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
