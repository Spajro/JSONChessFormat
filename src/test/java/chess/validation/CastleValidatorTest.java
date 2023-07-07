package chess.validation;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Rook;
import chess.results.ValidMoveResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CastleValidatorTest {

    @Test
    void shouldCastleWhiteShortTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.white, Position.of(8, 1), null));
        assertTrue(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 1), Position.of(7, 1))));
    }

    @Test
    void shouldCastleWhiteLongTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.white, Position.of(8, 1), null));
        assertTrue(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 1), Position.of(3, 1))));
    }

    @Test
    void shouldCastleBlackShortTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.black)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.black, Position.of(8, 8), null));
        assertTrue(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 8), Position.of(7, 8))));
    }

    @Test
    void shouldCastleBlackLongTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.black)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.black, Position.of(1, 8), null));
        assertTrue(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 8), Position.of(3, 8))));
    }

    @Test
    void shouldNotCastleWhiteShortWhenFieldIsOccupiedTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.white, Position.of(8, 1), null))
                .put(new Bishop(Color.white, Position.of(6, 1), null));
        assertFalse(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 1), Position.of(7, 1))));
    }

    @Test
    void shouldNotCastleWhiteShortWhenFieldIsCheckedTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.white, Position.of(8, 1), null))
                .put(new Rook(Color.black, Position.of(6, 8), null));
        assertFalse(new CastleValidator(chessBoard).isLegalCastle(new RawMove(Position.of(5, 1), Position.of(7, 1))));
    }

    @Test
    void shouldNotCastleWhiteShortKingMovedTest() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1), null))
                .put(new King(Color.black, Position.of(5, 8), null))
                .put(new Rook(Color.white, Position.of(8, 1), null))
                .put(new Bishop(Color.white, Position.of(6, 1), null));
        ChessBoard afterMove = ((ValidMoveResult) chessBoard.makeMove(new RawMove(Position.of(5, 1), Position.of(4, 1)))).getResult();
        assertFalse(new CastleValidator(afterMove).isLegalCastle(new RawMove(Position.of(4, 1), Position.of(7, 1))));
    }
}