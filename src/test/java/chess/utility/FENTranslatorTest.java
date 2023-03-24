package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.board.requirements.CastleRequirements;
import chess.color.Color;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FENTranslatorTest {

    @Test
    void parseFEN() {
        String fen = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - ";
        ChessBoard actual = new FENTranslator().parseFEN(fen);
        ChessBoard expected = new ChessBoard(Board.getBlank(),
                Color.white,
                new CastleRequirements(false, false, false, false),
                null
        )
                .put(new King(Color.white, new Position(1, 5), null))
                .put(new Pawn(Color.white, new Position(2, 5), null))
                .put(new Rook(Color.white, new Position(2, 4), null))
                .put(new Pawn(Color.white, new Position(5, 2), null))
                .put(new Pawn(Color.white, new Position(7, 2), null))
                .put(new Pawn(Color.black, new Position(3, 7), null))
                .put(new Pawn(Color.black, new Position(4, 6), null))
                .put(new Pawn(Color.black, new Position(6, 4), null))
                .put(new Rook(Color.black, new Position(8, 5), null))
                .put(new King(Color.black, new Position(8, 4), null));
        assertEquals(expected,actual);
    }

    @Test
    void parseFENOfStart() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";
        ChessBoard actual = new FENTranslator().parseFEN(fen);
        ChessBoard expected = new ChessBoard();
        assertEquals(expected, actual);
    }
}