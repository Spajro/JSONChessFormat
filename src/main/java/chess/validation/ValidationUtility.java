package chess.validation;

import chess.Position;
import chess.board.BoardWrapper;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.exceptions.ChessAxiomViolation;
import chess.moves.RawMove;
import chess.pieces.King;

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
}
