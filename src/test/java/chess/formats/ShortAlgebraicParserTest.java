package chess.formats;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.pieces.*;
import chess.formats.algebraic.ShortAlgebraicParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortAlgebraicParserTest {
    ShortAlgebraicParser parser = new ShortAlgebraicParser();

    @Test
    void pawnMoveTest() {
        String move = "e4";
        ChessBoard chessBoard = new ChessBoard();

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 2), Position.of(5, 4)), rawMove);
    }

    @Test
    void pawnCaptureTest() {
        String move = "xd5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, Position.of(5, 4)))
                .put(new Pawn(Color.black, Position.of(4, 5)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 4), Position.of(4, 5)), rawMove);
    }

    @Test
    void pieceMoveTest() {
        String move = "Nc3";
        ChessBoard chessBoard = new ChessBoard();

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(2, 1), Position.of(3, 3)), rawMove);
    }

    @Test
    void pieceCaptureTest() {
        String move = "Nxc3";
        ChessBoard chessBoard = new ChessBoard().put(new Pawn(Color.black, Position.of(3, 3)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(2, 1), Position.of(3, 3)), rawMove);
    }

    @Test
    void pawnPromotionTest() {
        String move = "e8=Q";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, Position.of(5, 7)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(new RawPromotion(Position.of(5, 7), Position.of(5, 8), Piece.Type.QUEEN), rawMove);
    }

    @Test
    void ambiguousByColumnPieceMoveTest() {
        String move = "Nbc5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(2, 3)))
                .put(new Knight(Color.white, Position.of(4, 3)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(2, 3), Position.of(3, 5)), rawMove);
    }

    @Test
    void ambiguousByRowPieceMoveTest() {
        String move = "N2e3";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(3, 2)))
                .put(new Knight(Color.white, Position.of(3, 4)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(3, 2), Position.of(5, 3)), rawMove);
    }

    @Test
    void ambiguousPawnCaptureTest() {
        String move = "cxd5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, Position.of(3, 4)))
                .put(new Pawn(Color.white, Position.of(5, 4)))
                .put(new Pawn(Color.black, Position.of(4, 5)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(3, 4), Position.of(4, 5)), rawMove);
    }

    @Test
    void pawnCapturePromotionTest() {
        String move = "xd8=Q";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, Position.of(5, 7)))
                .put(new Queen(Color.black, Position.of(4, 8)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(new RawPromotion(Position.of(5, 7), Position.of(4, 8), Piece.Type.QUEEN), rawMove);
    }

    @Test
    void ambiguousByColumnPieceCaptureTest() {
        String move = "Nbxc5";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(2, 3)))
                .put(new Knight(Color.white, Position.of(4, 3)))
                .put(new Pawn(Color.black, Position.of(3, 5)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(2, 3), Position.of(3, 5)), rawMove);
    }

    @Test
    void ambiguousByRowPieceCaptureTest() {
        String move = "N2xe3";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(3, 2)))
                .put(new Knight(Color.white, Position.of(3, 4)))
                .put(new Pawn(Color.black, Position.of(5, 3)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(3, 2), Position.of(5, 3)), rawMove);
    }

    @Test
    void doubleAmbiguousPieceToMoveTest() {
        String move = "Ne2d4";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(5, 2)))
                .put(new Knight(Color.white, Position.of(5, 6)))
                .put(new Knight(Color.white, Position.of(3, 2)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 2), Position.of(4, 4)), rawMove);
    }

    @Test
    void ambiguousPawnCapturePromotionTest() {
        String move = "exd8=Q";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Pawn(Color.white, Position.of(5, 7)))
                .put(new Pawn(Color.white, Position.of(3, 7)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(new RawPromotion(Position.of(5, 7), Position.of(4, 8), Piece.Type.QUEEN), rawMove);
    }

    @Test
    void doubleAmbiguousPieceCaptureToMoveTest() {
        String move = "Ne2xd4";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Knight(Color.white, Position.of(5, 2)))
                .put(new Knight(Color.white, Position.of(5, 6)))
                .put(new Knight(Color.white, Position.of(3, 2)))
                .put(new Pawn(Color.black, Position.of(4, 4)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 2), Position.of(4, 4)), rawMove);
    }

    @Test
    void shortCastleTest() {
        String move = "O-O";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Rook(Color.white, Position.of(8, 1)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 1), Position.of(7, 1)), rawMove);
    }

    @Test
    void longCastleTest() {
        String move = "O-O-O";
        ChessBoard chessBoard = getChessBoardWithKings()
                .put(new Rook(Color.white, Position.of(1, 1)));

        RawMove rawMove = parser.parseShortAlgebraic(move, chessBoard);

        assertEquals(RawMove.of(Position.of(5, 1), Position.of(3, 1)), rawMove);
    }

    private static ChessBoard getChessBoardWithKings() {
        return ChessBoard
                .getBlank(Color.white)
                .put(new King(Color.white, Position.of(5, 1)))
                .put(new King(Color.black, Position.of(5, 8)));
    }
}
