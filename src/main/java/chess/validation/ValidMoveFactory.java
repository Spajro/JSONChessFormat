package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.exceptions.IllegalCastleException;
import chess.exceptions.IllegalMoveException;
import chess.moves.*;

public class ValidMoveFactory {
    private final ChessBoard chessBoard;
    private final MoveValidator validator;

    public ValidMoveFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        validator = new MoveValidator(chessBoard);
    }

    public ValidMove createValidMove(RawMove move) throws IllegalMoveException {
        if (validator.isCorrect(move)) {
            if (validator.isLegalCastle(move)) {
                return createCastle(move);
            }
            if (validator.isLegalSimpleMove(move)) {
                return createSimpleMove(move);
            }
            if (validator.isLegalEnPassantCapture(move)) {
                return createEnPassantCapture(move);
            }
        }
        throw new IllegalMoveException("Move not correct");
    }

    private SimpleMove createSimpleMove(RawMove move) {
        return new SimpleMove(move, chessBoard.getBoard());
    }

    private EnPassantCapture createEnPassantCapture(RawMove move) {
        return new EnPassantCapture(move, chessBoard.getBoard());
    }

    private Castle createCastle(RawMove move) throws IllegalCastleException {
        return new Castle(move, getRookMove(move), chessBoard.getBoard());
    }

    private RawMove getRookMove(RawMove move) throws IllegalCastleException {
        if (validator.moveToType(move).equals(MoveValidator.CastleType.SHORT)) {
            return new RawMove(new Position(8, getStartRow()), new Position(6, getStartRow()));
        }
        if (validator.moveToType(move).equals(MoveValidator.CastleType.LONG)) {
            return new RawMove(new Position(1, getStartRow()), new Position(4, getStartRow()));
        }
        throw new IllegalStateException("Should not try to create Castle when its not Castle");
    }

    private int getStartRow() {
        if (chessBoard.getColor().isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }
}
