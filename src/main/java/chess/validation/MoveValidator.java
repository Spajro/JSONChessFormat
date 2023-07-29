package chess.validation;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.SimpleMove;
import chess.moves.valid.ValidMove;
import chess.pieces.Pawn;
import chess.pieces.Piece;

import java.util.Optional;

public class MoveValidator {
    private final ChessBoard chessBoard;
    private final CastleValidator castleValidator;
    private final CheckValidator checkValidator;


    public MoveValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        castleValidator = new CastleValidator(chessBoard);
        checkValidator = new CheckValidator(chessBoard);
    }

    public boolean isCorrect(RawMove move) {
        return chessBoard.getField(move.getStartPosition()).hasPiece()
                && chessBoard.getField(move.getStartPosition()).getPiece().getColor().equal(chessBoard.getColor());
    }

    public boolean isLegalSimpleMove(RawMove move) {
        return chessBoard.getField(move.getStartPosition()).getPiece().getPossibleEndPositions(chessBoard).contains(move.getEndPosition())
                && checkValidator.kingIsNotCheckedAfterSimpleMove(move, chessBoard.getColor());
    }

    public boolean isLegalEnPassantCapture(RawMove move) {
        if (chessBoard.getLastMove().isPresent()) {
            ValidMove lastMove = chessBoard.getLastMove().get();
            if (lastMove instanceof SimpleMove simpleMove) {
                if (chessBoard.getField(simpleMove.getEndPosition()).getPiece() instanceof Pawn) {
                    if (isFarPawnMove(simpleMove) && simpleMove.getStartPosition().getX() == move.getEndPosition().getX()) {
                        return ((move.getEndPosition().getY() == 6 && simpleMove.getStartPosition().getY() == 7)
                                || (move.getEndPosition().getY() == 3 && simpleMove.getStartPosition().getY() == 2))
                                && checkValidator.kingIsNotCheckedAfterEnPassantCapture(move, chessBoard.getColor());
                    }
                }
            }
        }
        return false;
    }

    public boolean isLegalCastle(RawMove move) {
        return castleValidator.isLegalCastle(move);
    }

    public boolean isLegalPromotion(RawMove move) {
        if (chessBoard.getField(move.getStartPosition()).getPiece().getType().equals(Piece.Type.PAWN)) {
            if (chessBoard.getColor().isWhite()) {
                return move.getStartPosition().getY() == 7 && move.getEndPosition().getY() == 8 && checkValidator.kingIsNotCheckedAfterPromotion(move,chessBoard.getColor());
            } else {
                return move.getStartPosition().getY() == 2 && move.getEndPosition().getY() == 1 && checkValidator.kingIsNotCheckedAfterPromotion(move,chessBoard.getColor());
            }
        }
        return false;
    }

    private boolean isFarPawnMove(SimpleMove simpleMove) {
        return (simpleMove.getStartPosition().getY() == 2 && simpleMove.getEndPosition().getY() == 4)
                || (simpleMove.getStartPosition().getY() == 7 && simpleMove.getEndPosition().getY() == 5);
    }

    public Optional<CastleValidator.CastleType> moveToType(RawMove move) {
        return castleValidator.moveToType(move);
    }
}
