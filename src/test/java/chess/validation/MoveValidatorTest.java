package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.RawMove;

import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveValidatorTest {

    @Test
    void shouldMoveWhenKingIsNotChecked() {
        ChessBoard board = ChessBoard.getBlank(Color.white);
        board = board.put(new King(Color.white, new Position(1, 1), board));
        board = board.put(new King(Color.black, new Position(8, 8), board));
        board = board.put(new Knight(Color.white, new Position(4, 5), board));
        assertTrue(board.makeMove(new RawMove(new Position(4, 5), new Position(5, 7))).isValid());
    }

    @Test
    void shouldNotMoveWhenKingIsChecked() {
        ChessBoard board = ChessBoard.getBlank(Color.white);
        board=board.put(new King(Color.white, new Position(1, 1), board));
        board=board.put(new King(Color.black, new Position(8, 8), board));
        board=board.put(new Rook(Color.black, new Position(1, 8), board));
        board=board.put(new Knight(Color.white, new Position(4, 5), board));
        assertFalse(board.makeMove(new RawMove(new Position(4, 5), new Position(5, 7))).isValid());
    }

}