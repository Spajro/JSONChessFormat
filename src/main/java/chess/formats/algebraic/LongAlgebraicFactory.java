package chess.formats.algebraic;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.moves.valid.ValidMove;
import chess.moves.valid.executable.Castle;
import chess.moves.valid.executable.EnPassantCapture;
import chess.moves.valid.executable.Promotion;
import chess.moves.valid.executable.SimpleMove;
import chess.pieces.Piece;

import java.util.Optional;

public class LongAlgebraicFactory {
    private static final LongAlgebraicFactory longAlgebraicFactory = new LongAlgebraicFactory();

    private LongAlgebraicFactory() {
    }

    public static LongAlgebraicFactory getInstance() {
        return longAlgebraicFactory;
    }

    AlgebraicUtility utility = AlgebraicUtility.getInstance();

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

    public String moveToLongAlgebraic(ChessBoard board, RawMove move) {
        Optional<String> castle = utility.castleToAlgebraic(move);
        if (castle.isPresent()
                && board.getField(move.getStartPosition()).hasPiece()
                && board.getField(move.getStartPosition()).getPiece().getType().equals(Piece.Type.KING)) {
            return castle.get();
        }

        if (move instanceof RawPromotion promotion) {
            return utility.positionToAlgebraic(promotion.getStartPosition()) +
                    "-" +
                    utility.positionToAlgebraic(promotion.getEndPosition()) +
                    "=" +
                    utility.typeToAlgebraic(promotion.getType());
        }

        return utility.typeToAlgebraic(board.getField(move.getStartPosition()).getPiece().getType()) +
                utility.positionToAlgebraic(move.getStartPosition()) +
                "-" +
                utility.positionToAlgebraic(move.getEndPosition());
    }
}
