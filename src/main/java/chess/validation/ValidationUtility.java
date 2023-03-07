package chess.validation;

import chess.Position;
import chess.board.BoardWrapper;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.exceptions.ChessAxiomViolation;
import chess.moves.RawMove;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.RestrictedMovementPiece;

class ValidationUtility {
    private final ChessBoard chessBoard;

    public ValidationUtility(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isKingChecked(Color kingColor) throws ChessAxiomViolation {
        return chessBoard.isPositionAttacked(getKingPosition(kingColor), kingColor.swap());
    }

    public boolean isKingEscapingFromCheck(RawMove move, Color kingColor) {
        return BoardWrapper.getFieldFromBoard(chessBoard, move.getStartPosition()) instanceof King
                && !chessBoard.isPositionAttacked(move.getEndPosition(), kingColor.swap());
    }

    public Position getKingPosition(Color kingColor) throws ChessAxiomViolation {
        return chessBoard.getPiecesOfColor(kingColor).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new ChessAxiomViolation("No King on Board"))
                .getPosition();
    }

    public boolean isRemovingCheck(RawMove move, Color kingColor) throws ChessAxiomViolation {
        Position kingPosition = getKingPosition(kingColor);
        long kingPositionAttackedTimes = chessBoard.getNumberOfPiecesAttackingFields(kingColor.swap()).get(kingPosition);
        if (kingPositionAttackedTimes > 2) {
            throw new ChessAxiomViolation("Impossible multi-check");
        }
        if (kingPositionAttackedTimes == 1) {
            Piece attackingPiece = chessBoard.getPiecesAttackingPosition(kingPosition, kingColor.swap()).stream().findFirst().orElseThrow();
            if (move.getEndPosition().equals(attackingPiece.getPosition())) {
                if (move.getStartPosition().equals(kingPosition)) {
                    return !chessBoard.isPositionAttacked(move.getEndPosition(), kingColor.swap());
                } else {
                    return true;
                }
            } else if (attackingPiece instanceof RestrictedMovementPiece) {
                return move.getEndPosition().isBetween(attackingPiece.getPosition(), kingPosition);
            }
        }
        return false;
    }
}
