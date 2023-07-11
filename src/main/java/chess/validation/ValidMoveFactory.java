package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.moves.valid.UnTypedPromotion;
import chess.moves.valid.ValidMove;
import chess.moves.valid.executable.Castle;
import chess.moves.valid.executable.EnPassantCapture;
import chess.moves.valid.executable.Promotion;
import chess.moves.valid.executable.SimpleMove;

import java.util.Optional;

public class ValidMoveFactory {
    private final ChessBoard chessBoard;
    private final MoveValidator validator;

    public ValidMoveFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        validator = new MoveValidator(chessBoard);
    }

    public Optional<ValidMove> createValidMove(RawMove move) {
        if (validator.isCorrect(move)) {
            if (validator.isLegalPromotion(move)) {
                return Optional.of(createPromotion(move));
            }
            if (validator.isLegalCastle(move)) {
                return Optional.of(createCastle(move));
            }
            if (validator.isLegalSimpleMove(move)) {
                return Optional.of(createSimpleMove(move));
            }
            if (validator.isLegalEnPassantCapture(move)) {
                return Optional.of(createEnPassantCapture(move));
            }
        }
        return Optional.empty();
    }

    private ValidMove createPromotion(RawMove move) {
        if (move instanceof RawPromotion rawPromotion) {
            return new Promotion(move, chessBoard.getBoard(), chessBoard.getColor(), rawPromotion.getType());
        }
        return new UnTypedPromotion(move, chessBoard.getBoard(), chessBoard.getColor());
    }

    private SimpleMove createSimpleMove(RawMove move) {
        return new SimpleMove(move, chessBoard.getBoard());
    }

    private EnPassantCapture createEnPassantCapture(RawMove move) {
        return new EnPassantCapture(move, chessBoard.getBoard());
    }

    private Castle createCastle(RawMove move) {
        return new Castle(move, getRookMove(move), chessBoard.getBoard());
    }

    private RawMove getRookMove(RawMove move) {
        CastleValidator.CastleType moveType = validator.moveToType(move).orElseThrow();
        if (moveType.equals(CastleValidator.CastleType.SHORT)) {
            return RawMove.of(Position.of(8, getStartRow()), Position.of(6, getStartRow()));
        }
        if (moveType.equals(CastleValidator.CastleType.LONG)) {
            return RawMove.of(Position.of(1, getStartRow()), Position.of(4, getStartRow()));
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
