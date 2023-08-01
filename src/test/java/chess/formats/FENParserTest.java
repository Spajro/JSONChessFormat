package chess.formats;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.board.requirements.CastleRequirements;
import chess.color.Color;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import chess.formats.fen.FENParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FENParserTest {

    @Test
    void parseFEN() {
        String fen = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - ";
        ChessBoard actual = FENParser.getInstance().parseFEN(fen);
        ChessBoard expected = new ChessBoard(Board.getBlank(),
                Color.white,
                new CastleRequirements(false, false, false, false),
                null,
                null,
                null
        )
                .put(new King(Color.white, Position.of(1, 5)))
                .put(new Pawn(Color.white, Position.of(2, 5)))
                .put(new Rook(Color.white, Position.of(2, 4)))
                .put(new Pawn(Color.white, Position.of(5, 2)))
                .put(new Pawn(Color.white, Position.of(7, 2)))
                .put(new Pawn(Color.black, Position.of(3, 7)))
                .put(new Pawn(Color.black, Position.of(4, 6)))
                .put(new Pawn(Color.black, Position.of(6, 4)))
                .put(new Rook(Color.black, Position.of(8, 5)))
                .put(new King(Color.black, Position.of(8, 4)));
        assertEquals(expected, actual);
    }

    @Test
    void parseFENOfStart() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";
        ChessBoard actual = FENParser.getInstance().parseFEN(fen);
        ChessBoard expected = new ChessBoard();
        assertEquals(expected, actual);
    }
}