package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.features.ChessBoardUtility;
import chess.color.Color;
import chess.moves.*;
import chess.pieces.King;

class CheckValidator {
    private final ChessBoardUtility utility;
    private final ChessBoard chessBoard;

    CheckValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.utility = chessBoard.getUtility();
    }

    public boolean isKingChecked(Color kingColor) {
        return utility.isPositionAttacked(getKingPosition(kingColor), kingColor.swap());
    }

    public Position getKingPosition(Color kingColor) {
        return utility.getPiecesOfColor(kingColor).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No King on Board"))
                .getPosition();
    }

    public boolean kingIsNotCheckedAfterSimpleMove(RawMove move, Color kingColor) {
        ChessBoard tempBoard = chessBoard.makeMove((ExecutableMove) new SimpleMove(move, chessBoard.getBoard()));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }

    public boolean kingIsNotCheckedAfterEnPassantCapture(RawMove move, Color kingColor) {
        ChessBoard tempBoard = chessBoard.makeMove((ExecutableMove) new EnPassantCapture(move, chessBoard.getBoard()));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }
}
