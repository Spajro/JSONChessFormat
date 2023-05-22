package chess.results;

import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import data.model.PromotionTypeProvider;

import java.util.Optional;

public interface MoveResult {
    boolean isValid();

    RawMove getMove();

    default Optional<ValidMoveResult> validate(PromotionTypeProvider typeProvider) {
        if (!this.isValid()) {
            return Optional.empty();
        }
        ValidMoveResult validMoveResult;
        if (this instanceof PromotionResult promotionResult) {
            if (getMove() instanceof RawPromotion rawPromotion) {
                validMoveResult = promotionResult.type(rawPromotion.getType());
            } else {
                validMoveResult = promotionResult.type(typeProvider.getPromotionType());
            }
        } else {
            validMoveResult = (ValidMoveResult) this;
        }
        return Optional.ofNullable(validMoveResult);
    }
}
