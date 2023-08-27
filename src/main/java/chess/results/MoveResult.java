package chess.results;

import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import data.model.PromotionTypeProvider;

import java.util.Optional;

public interface MoveResult {
    boolean isValid();

    RawMove getMove();

    default Optional<ValidMoveResult> validate() {
        if (!this.isValid()) {
            return Optional.empty();
        }
        ValidMoveResult validMoveResult;
        if (this instanceof PromotionResult promotionResult) {
            if (getMove() instanceof RawPromotion rawPromotion) {
                validMoveResult = promotionResult.type(rawPromotion.getType());
            } else {
                throw new IllegalArgumentException("No promotion info");
            }
        } else {
            validMoveResult = (ValidMoveResult) this;
        }
        return Optional.ofNullable(validMoveResult);
    }

    default Optional<ValidMoveResult> validate(PromotionTypeProvider typeProvider) {
        if (!this.isValid()) {
            return Optional.empty();
        }
        ValidMoveResult validMoveResult;
        if (this instanceof PromotionResult promotionResult) {
            validMoveResult = promotionResult.type(typeProvider.getPromotionType());
        } else {
            validMoveResult = (ValidMoveResult) this;
        }
        return Optional.ofNullable(validMoveResult);
    }
}
