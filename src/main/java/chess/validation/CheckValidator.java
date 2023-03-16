package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.features.ChessBoardUtility;
import chess.color.Color;
import chess.exceptions.ChessAxiomViolation;
import chess.moves.RawMove;
import chess.moves.SimpleMove;
import chess.moves.ValidMove;
import chess.pieces.King;

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

    public Position getKingPosition(Color kingColor) throws ChessAxiomViolation {
        return utility.getPiecesOfColor(kingColor).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new ChessAxiomViolation("No King on Board"))
                .getPosition();
    }

    public boolean kingIsNotCheckedAfterMove(RawMove move, Color kingColor) throws ChessAxiomViolation {
        ChessBoard tempBoard = chessBoard.makeMove((ValidMove) new SimpleMove(move, chessBoard.getBoard()));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }
}
