package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.raw.RawMove;

import chess.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveValidatorTest {

    @Test
    void shouldMoveWhenKingIsNotChecked() {
        ChessBoard board = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(1, 1), null))
                .put(new King(Color.black, Position.of(8, 8), null))
                .put(new Knight(Color.white, Position.of(4, 5), null));
        assertTrue(board.makeMove(RawMove.of(Position.of(4, 5), Position.of(5, 7))).isValid());
    }

    @Test
    void shouldNotMoveWhenKingIsChecked() {
        ChessBoard board = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(1, 1), null))
                .put(new King(Color.black, Position.of(8, 8), null))
                .put(new Rook(Color.black, Position.of(1, 8), null))
                .put(new Knight(Color.white, Position.of(4, 5), null));
        assertFalse(board.makeMove(RawMove.of(Position.of(4, 5), Position.of(5, 7))).isValid());
    }

    @Test
    void shouldNotCaptureDefendedPieceWithKing() {
        ChessBoard board = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new Bishop(Color.black, Position.of(2, 6), null))
                .put(new Queen(Color.black, Position.of(6, 2), null));
        assertFalse(board.makeMove(RawMove.of(Position.of(5, 1), Position.of(6, 2))).isValid());
    }

}