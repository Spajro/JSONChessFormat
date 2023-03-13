package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.ChessBoardUtility;
import chess.color.Color;
import chess.exceptions.ChessAxiomViolation;
import chess.moves.RawMove;
import chess.moves.SimpleMove;
import chess.moves.ValidMove;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.RestrictedMovementPiece;

class CheckValidator {
    private final ChessBoardUtility utility;
    private final ChessBoard chessBoard;

    CheckValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.utility = chessBoard.getUtility();
    }

    public boolean isKingChecked(Color kingColor) throws ChessAxiomViolation {
        return utility.isPositionAttacked(getKingPosition(kingColor), kingColor.swap());
    }

    public boolean isKingEscapingFromCheck(RawMove move, Color kingColor) throws ChessAxiomViolation {
        return getKingPosition(kingColor).equals(move.getStartPosition())
                && !utility.isPositionAttacked(move.getEndPosition(), kingColor.swap());
    }

    public Position getKingPosition(Color kingColor) throws ChessAxiomViolation {
        return utility.getPiecesOfColor(kingColor).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new ChessAxiomViolation("No King on Board"))
                .getPosition();
    }

    public boolean isRemovingCheck(RawMove move, Color kingColor) throws ChessAxiomViolation {
        Position kingPosition = getKingPosition(kingColor);
        long kingPositionAttackedTimes = utility.getNumberOfPiecesAttackingFields(kingColor.swap()).get(kingPosition);
        if (kingPositionAttackedTimes > 2) {
            throw new ChessAxiomViolation("Impossible multi-check");
        }
        if (kingPositionAttackedTimes == 1) {
            Piece attackingPiece = utility.getPiecesAttackingPosition(kingPosition, kingColor.swap()).stream().findFirst().orElseThrow();
            if (move.getEndPosition().equals(attackingPiece.getPosition())) {
                if (move.getStartPosition().equals(kingPosition)) {
                    return !utility.isPositionAttacked(move.getEndPosition(), kingColor.swap());
                } else {
                    return true;
                }
            } else if (attackingPiece instanceof RestrictedMovementPiece) {
                return move.getEndPosition().isBetween(attackingPiece.getPosition(), kingPosition) && !move.getStartPosition().equals(kingPosition);
            }
        }
        return false;
    }

    public boolean isMovingPinnedPiece(RawMove move, Color kingColor) throws ChessAxiomViolation {
        ChessBoard tempBoard = chessBoard.makeMove((ValidMove) new SimpleMove(move, chessBoard.getBoard()));
        return !isKingChecked(kingColor) && (new CheckValidator(tempBoard).isKingChecked(kingColor));
    }
}
