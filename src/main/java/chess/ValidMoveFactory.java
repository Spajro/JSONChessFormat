package chess;

import chess.board.ChessBoard;
import chess.exceptions.ChessAxiomViolation;
import chess.exceptions.IllegalCastleException;
import chess.exceptions.IllegalMoveException;
import chess.moves.Castle;
import chess.moves.RawMove;
import chess.moves.SimpleMove;
import chess.moves.ValidMove;

public class ValidMoveFactory {
    private final ChessBoard chessBoard;
    private final MoveValidator validator;

    public ValidMoveFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        validator = new MoveValidator(chessBoard);
    }

    public ValidMove createValidMove(RawMove move) throws IllegalMoveException, ChessAxiomViolation {
        if (validator.isCorrect(move)) {
            if (validator.isLegalCastle(move)) {
                return createCastle(move);
            }
            if (validator.isLegalSimpleMove(move)) {
                return createSimpleMove(move);
            }
        }
        throw new IllegalMoveException("Move not correct");
    }

    private Castle createCastle(RawMove move) throws ChessAxiomViolation, IllegalCastleException {
        return new Castle(move, getRookMove(move), chessBoard.getBoard());
    }

    private RawMove getRookMove(RawMove move) throws ChessAxiomViolation, IllegalCastleException {
        if (validator.moveToType(move).equals(MoveValidator.CastleType.SHORT)) {
            return new RawMove(new Position(8, getStartRow()), new Position(6, getStartRow()));
        }
        if (validator.moveToType(move).equals(MoveValidator.CastleType.LONG)) {
            return new RawMove(new Position(1, getStartRow()), new Position(4, getStartRow()));
        }
        throw new IllegalStateException("Should not try to create Castle when its not Castle");
    }

    private SimpleMove createSimpleMove(RawMove move) {
        return new SimpleMove(move, chessBoard.getBoard());
    }

    private int getStartRow() {
        if (chessBoard.getColor().isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }
}