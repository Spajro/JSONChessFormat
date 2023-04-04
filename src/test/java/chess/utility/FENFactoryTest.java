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

class FENFactoryTest {
    @Test
    void chessBoardToFENTest() {
        ChessBoard chessBoard = new ChessBoard(Board.getBlank(),
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

        String expected = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -";
        String actual = FENFactory.getInstance().chessBoardToFEN(chessBoard);

        assertEquals(expected, actual);
    }

    @Test
    void chessBoardToFENOfStart() {
        String expected = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -";
        String actual = FENFactory.getInstance().chessBoardToFEN(new ChessBoard());
        assertEquals(expected, actual);
    }
}