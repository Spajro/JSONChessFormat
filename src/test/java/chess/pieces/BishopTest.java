package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void shouldReturn7PositionsForBishopInCorner() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Bishop bishop = new Bishop(Color.white, Position.of(1, 1));

        assertEquals(7, bishop.getPossibleEndPositions(chessBoard).size());
    }

    @Test
    void shouldReturn13PositionsForBishopInMiddle() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Bishop bishop = new Bishop(Color.white, Position.of(4, 4));

        assertEquals(13, bishop.getPossibleEndPositions(chessBoard).size());
    }

    @Test
    void shouldReturn1PositionsForBlockedBishopInCorner() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(2, 2)));

        Bishop bishop = new Bishop(Color.white, Position.of(1, 1));

        assertEquals(1, bishop.getPossibleEndPositions(chessBoard).size());
    }
}