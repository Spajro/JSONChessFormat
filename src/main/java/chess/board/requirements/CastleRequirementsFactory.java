package chess.board.requirements;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.moves.ValidMove;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;

public class CastleRequirementsFactory {
    private final ChessBoard chessBoard;

    public CastleRequirementsFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public CastleRequirements getNextRequirements(ValidMove move) {
        if (kingMoved(move)) {
            return chessBoard.getCastleRequirements().kingMoved(chessBoard.getColor());
        } else if (hColumnRookMoved(move)) {
            return chessBoard.getCastleRequirements().hColumnRookMoved(chessBoard.getColor());
        } else if (aColumnRookMoved(move)) {
            return chessBoard.getCastleRequirements().aColumnRookMoved(chessBoard.getColor());
        } else {
            return chessBoard.getCastleRequirements().copy();
        }
    }

    private boolean aColumnRookMoved(ValidMove move) {
        return isRookOnPosition(move.getStartPosition()) && move.getStartPosition().getX() == 1 && move.getStartPosition().getY() == getStartRow();
    }

    private boolean hColumnRookMoved(ValidMove move) {
        return isRookOnPosition(move.getStartPosition()) && move.getStartPosition().getX() == 8 && move.getStartPosition().getY() == getStartRow();
    }

    private boolean kingMoved(ValidMove move) {
        return isKingOnPosition(move.getStartPosition()) && move.getStartPosition().getX() == 5 && move.getStartPosition().getY() == getStartRow();
    }

    private boolean isRookOnPosition(Position position) {
        Field field = chessBoard.getField(position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof Rook) && piece.getColor().equal(chessBoard.getColor());
    }

    private boolean isKingOnPosition(Position position) {
        Field field = chessBoard.getField(position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof King) && piece.getColor().equal(chessBoard.getColor());
    }

    private int getStartRow() {
        if (chessBoard.getColor().isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }
}
