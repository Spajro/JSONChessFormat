package chess.board.requirements;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.moves.valid.ValidMove;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;

public class CastleRequirementsFactory {
    private final ChessBoard chessBoard;

    public CastleRequirementsFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public CastleRequirements getNextRequirements(ValidMove move) {
        CastleRequirements requirements = chessBoard.getCastleRequirements();
        if (kingMoved(move)) {
            return requirements.kingMoved(chessBoard.getColor());
        } else if (hColumnRookMoved(move)) {
            return requirements.hColumnRookMoved(chessBoard.getColor());
        } else if (hColumnRookCaptured(move)) {
            return requirements.hColumnRookMoved(chessBoard.getColor().swap());
        } else if (aColumnRookMoved(move)) {
            return requirements.aColumnRookMoved(chessBoard.getColor());
        } else if (aColumnRookCaptured(move)) {
            return requirements.aColumnRookMoved(chessBoard.getColor().swap());
        } else {
            return requirements.copy();
        }
    }

    private boolean aColumnRookMoved(ValidMove move) {
        return isRookOnPosition(move.getRepresentation().getStartPosition())
                && move.getRepresentation().getStartPosition().equals(Position.of(1, getStartRow()));
    }

    private boolean aColumnRookCaptured(ValidMove move) {
        return isOppositeRookOnPosition(move.getRepresentation().getEndPosition())
                && move.getRepresentation().getEndPosition().equals(Position.of(1, getOppositeStartRow()));
    }

    private boolean hColumnRookMoved(ValidMove move) {
        return isRookOnPosition(move.getRepresentation().getStartPosition())
                && move.getRepresentation().getStartPosition().equals(Position.of(8, getStartRow()));
    }

    private boolean hColumnRookCaptured(ValidMove move) {
        return isOppositeRookOnPosition(move.getRepresentation().getEndPosition())
                && move.getRepresentation().getEndPosition().equals(Position.of(8, getOppositeStartRow()));
    }

    private boolean kingMoved(ValidMove move) {
        return isKingOnPosition(move.getRepresentation().getStartPosition())
                && move.getRepresentation().getStartPosition().getX() == 5
                && move.getRepresentation().getStartPosition().getY() == getStartRow();
    }

    private boolean isRookOnPosition(Position position) {
        Field field = chessBoard.getField(position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof Rook) && piece.getColor().equal(chessBoard.getColor());
    }

    private boolean isOppositeRookOnPosition(Position position) {
        Field field = chessBoard.getField(position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof Rook) && !piece.getColor().equal(chessBoard.getColor());
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

    private int getOppositeStartRow() {
        if (chessBoard.getColor().isWhite()) {
            return 8;
        } else {
            return 1;
        }
    }
}
