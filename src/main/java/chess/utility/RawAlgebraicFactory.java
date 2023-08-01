package chess.utility;

import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;

public class RawAlgebraicFactory {
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();

    public String moveToRawAlgebraic(RawMove rawMove) {
        String start = algebraicUtility.positionToAlgebraic(rawMove.getStartPosition());
        String end = algebraicUtility.positionToAlgebraic(rawMove.getEndPosition());
        String promotion = "";
        if (rawMove instanceof RawPromotion rawPromotion) {
            promotion = String.valueOf(algebraicUtility.typeToAlgebraic(rawPromotion.getType()));
        }
        return start + end + promotion;
    }

}
