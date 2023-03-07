package chess.validation;

import chess.board.ChessBoard;
import chess.exceptions.ChessAxiomViolation;
import chess.exceptions.IllegalCastleException;
import chess.moves.RawMove;
import chess.moves.SimpleMove;
import chess.moves.ValidMove;
import chess.pieces.Pawn;

public class MoveValidator {
    private final ChessBoard chessBoard;
    private final CastleValidator castleValidator;
    private final ValidationUtility validationUtility;

    enum CastleType {
        SHORT, LONG
    }

    public MoveValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        castleValidator = new CastleValidator(chessBoard);
        validationUtility = new ValidationUtility(chessBoard);
    }

    public boolean isCorrect(RawMove move) {
        return chessBoard.getField(move.getStartPosition()).hasPiece()
                && chessBoard.getField(move.getStartPosition()).getPiece().getColor().equal(chessBoard.getColor());
    }

    public boolean isLegalSimpleMove(RawMove move) throws ChessAxiomViolation {
        return chessBoard.getField(move.getStartPosition()).getPiece().getPossibleEndPositions().contains(move.getEndPosition())
                &&
                (!validationUtility.isKingChecked(chessBoard.getColor())
                        || validationUtility.isKingEscapingFromCheck(move, chessBoard.getColor())
                        || validationUtility.isRemovingCheck(move, chessBoard.getColor()));
    }

    public boolean isLegalEnPassantCapture(RawMove move) {
        if (chessBoard.getLastMove().isPresent()) {
            ValidMove lastMove = chessBoard.getLastMove().get();
            if (lastMove instanceof SimpleMove simpleMove) {
                if (chessBoard.getField(simpleMove.getEndPosition()).getPiece() instanceof Pawn) {
                    if (isFarPawnMove(simpleMove) && simpleMove.getStartPosition().getX() == move.getEndPosition().getX()) {
                        return (move.getEndPosition().getY() == 6 && simpleMove.getStartPosition().getY() == 7)
                                || (move.getEndPosition().getY() == 3 && simpleMove.getStartPosition().getY() == 2);
                    }
                }
            }
        }
        return false;
    }

    public boolean isLegalCastle(RawMove move) throws ChessAxiomViolation {
        return castleValidator.isLegalCastle(move);
    }

    private boolean isFarPawnMove(SimpleMove simpleMove) {
        return (simpleMove.getStartPosition().getY() == 2 && simpleMove.getEndPosition().getY() == 4)
                || (simpleMove.getStartPosition().getY() == 7 && simpleMove.getEndPosition().getY() == 5);
    }

    public CastleType moveToType(RawMove move) throws ChessAxiomViolation, IllegalCastleException {
        return castleValidator.moveToType(move);
    }
}
