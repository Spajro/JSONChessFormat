package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.RawMove;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;
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
        ChessBoard chessBoard = getChessBoardWithKings()
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

    @Test
    void parseShortCastleTest() {
        String move = "O-O";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Rook(Color.white, new Position(8, 1), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(5, 1), new Position(7, 1)), rawMove);
    }

    @Test
    void parseLongCastleTest() {
        String move = "O-O-O";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Rook(Color.white, new Position(1, 1), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(5, 1), new Position(3, 1)), rawMove);
    }

    @Test
    void ambiguousPawnCaptureTest() {
        String move = "cxd5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, new Position(3, 4), null))
                .put(new Pawn(Color.white, new Position(5, 4), null))
                .put(new Pawn(Color.black, new Position(4, 5), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(3, 4), new Position(4, 5)), rawMove);
    }

    @Test
    void ambiguousByColumnPieceMoveTest() {
        String move = "Nbc5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, new Position(2, 3), null))
                .put(new Knight(Color.white, new Position(4, 3), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(2, 3), new Position(3, 5)), rawMove);
    }

    @Test
    void ambiguousByRowPieceMoveTest() {
        String move = "N2e3";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, new Position(3, 2), null))
                .put(new Knight(Color.white, new Position(3, 4), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(3, 2), new Position(5, 3)), rawMove);
    }

    @Test
    void ambiguousByColumnPieceCaptureTest() {
        String move = "Nbxc5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, new Position(2, 3), null))
                .put(new Knight(Color.white, new Position(4, 3), null))
                .put(new Pawn(Color.black, new Position(3, 5), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(2, 3), new Position(3, 5)), rawMove);
    }

    @Test
    void ambiguousByRowPieceCaptureTest() {
        String move = "N2xe3";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, new Position(3, 2), null))
                .put(new Knight(Color.white, new Position(3, 4), null))
                .put(new Pawn(Color.black, new Position(5, 3), null));
        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);
        assertEquals(new RawMove(new Position(3, 2), new Position(5, 3)), rawMove);
    }

    private static ChessBoard getChessBoardWithKings() {
        return ChessBoard
                .getBlank(Color.white)
                .put(new King(Color.white, new Position(5, 1), null))
                .put(new King(Color.black, new Position(5, 8), null));
    }
}
