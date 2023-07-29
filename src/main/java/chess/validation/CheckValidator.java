package chess.validation;

import chess.board.ChessBoard;
import chess.board.features.ChessBoardUtility;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.EnPassantCapture;
import chess.moves.valid.executable.Promotion;
import chess.moves.valid.executable.SimpleMove;
import chess.pieces.Piece;

class CheckValidator {
    private final ChessBoardUtility utility;
    private final ChessBoard chessBoard;

    CheckValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.utility = chessBoard.getUtility();
    }

    public boolean isKingChecked(Color kingColor) {
        return utility.isPositionAttacked(chessBoard.getKingPosition(kingColor), kingColor.swap());
    }

    public boolean kingIsNotCheckedAfterSimpleMove(RawMove move, Color kingColor) {
        ChessBoard tempBoard = chessBoard.makeMove(new SimpleMove(move, chessBoard.getBoard()));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }

    public boolean kingIsNotCheckedAfterEnPassantCapture(RawMove move, Color kingColor) {
        ChessBoard tempBoard = chessBoard.makeMove(new EnPassantCapture(move, chessBoard.getBoard()));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }

    public boolean kingIsNotCheckedAfterPromotion(RawMove move, Color kingColor) {
        ChessBoard tempBoard = chessBoard.makeMove(new Promotion(move, chessBoard.getBoard(), chessBoard.getColor(), Piece.Type.QUEEN));
        return !(new CheckValidator(tempBoard).isKingChecked(kingColor));
    }
}
