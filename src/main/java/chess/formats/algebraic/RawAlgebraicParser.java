package chess.formats.algebraic;

import chess.Position;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.pieces.Piece;

import java.util.Optional;

public class RawAlgebraicParser {
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();

    public RawMove rawAlgebraicToMoves(String move) {
        if (move.length() < 4 || move.length() > 5) {
            throw new IllegalArgumentException("illegal RawAlgebraic: " + move);
        }
        Optional<Position> start = algebraicUtility.algebraicToPosition(move.substring(0, 2));
        Optional<Position> end = algebraicUtility.algebraicToPosition(move.substring(2, 4));
        if (move.length() == 4) {
            return RawMove.of(start.orElseThrow(), end.orElseThrow());
        }
        Optional<Piece.Type> promotion = algebraicUtility.algebraicToType(move.charAt(4));
        return new RawPromotion(start.orElseThrow(), end.orElseThrow(), promotion.orElseThrow());
    }
}
