package chess.utility;

import chess.board.ChessBoard;
import chess.moves.*;

public class LongAlgebraicFactory {
    AlgebraicUtility utility=new AlgebraicUtility();

    public String moveToLongAlgebraic(ChessBoard board, ValidMove move) {
        if (move instanceof SimpleMove simpleMove) {
            return utility.typeToAlgebraic(board.getField(simpleMove.getStartPosition()).getPiece().getType()) +
                    utility.positionToAlgebraic(simpleMove.getStartPosition()) +
                    "-" +
                    utility.positionToAlgebraic(simpleMove.getEndPosition());
        } else if (move instanceof Castle castle) {
            return switch (castle.getType()) {
                case SHORT -> "O-O";
                case LONG -> "O-O-O";
            };
        } else if (move instanceof EnPassantCapture enPassantCapture) {
            return utility.positionToAlgebraic(enPassantCapture.getStartPosition()) +
                    "-" +
                    utility.positionToAlgebraic(enPassantCapture.getEndPosition());
        } else if (move instanceof Promotion promotion) {
            return utility.positionToAlgebraic(promotion.getStartPosition()) +
                    "-" +
                    utility.positionToAlgebraic(promotion.getEndPosition()) +
                    "=" +
                    utility.typeToAlgebraic(promotion.getType());
        }
        throw new IllegalStateException();
    }
}
