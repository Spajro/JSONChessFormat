package chess.utility;

import chess.Position;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.pieces.Piece;

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
        Optional<Position> end = utility.algebraicToPosition(rawMove.substring(3, 5));
        if (start.isEmpty() || end.isEmpty()) {
            return Optional.empty();
        }
        if (move.length() == 7 && move.charAt(5) == '=') {
            Optional<Piece.Type> type = utility.algebraicToType(move.charAt(6));
            if (type.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new RawPromotion(
                    start.get(),
                    end.get(),
                    type.get()
            ));

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
