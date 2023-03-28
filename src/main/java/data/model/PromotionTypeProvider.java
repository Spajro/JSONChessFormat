package data.model;

import chess.pieces.Piece;

public interface PromotionTypeProvider {
    Piece.Type getPromotionType();
}
