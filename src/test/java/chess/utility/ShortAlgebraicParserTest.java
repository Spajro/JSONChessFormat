package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.RawMove;
import chess.pieces.King;
import chess.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortAlgebraicParserTest {
    ShortAlgebraicParser parser = new ShortAlgebraicParser();

    @Test
    void parsePawnMoveTest() {
        String move = "e4";
        ChessBoard chessBoard = new ChessBoard();
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(5, 2), new Position(5, 4)), rawMove);
    }

    @Test
    void parsePawnCaptureTest() {
        String move = "xd5";
        ChessBoard chessBoard = ChessBoard
                .getBlank(Color.white)
                .put(new King(Color.white, new Position(5, 1), null))
                .put(new King(Color.black, new Position(5, 8), null))
                .put(new Pawn(Color.white, new Position(5, 4), null))
                .put(new Pawn(Color.black, new Position(4, 5), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(5, 4), new Position(4, 5)), rawMove);
    }

    @Test
    void parsePieceMoveTest() {
        String move = "Nc3";
        ChessBoard chessBoard = new ChessBoard();
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(2, 1), new Position(3, 3)), rawMove);
    }

    @Test
    void parsePieceCaptureTest() {
        String move = "Nxc3";
        ChessBoard chessBoard = new ChessBoard().put(new Pawn(Color.black, new Position(3, 3), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(2, 1), new Position(3, 3)), rawMove);
    }
}
