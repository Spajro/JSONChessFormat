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

    public boolean isKingChecked(Color color) throws ChessAxiomViolation {
        return chessBoard.isPositionAttacked(getKingPosition(color));
    }

    public boolean isKingEscapingFromCheck(RawMove move) {
        return BoardWrapper.getFieldFromBoard(chessBoard, move.getStartPosition()) instanceof King
                && !chessBoard.isPositionAttacked(move.getEndPosition());
    }

    public Position getKingPosition(Color color) throws ChessAxiomViolation {
        return chessBoard.getPiecesOfColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new ChessAxiomViolation("No King on Board"))
                .getPosition();
    }
}
