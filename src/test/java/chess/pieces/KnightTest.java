package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void shouldReturn8PositionsForKnightInMiddle() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);
        Knight knight = new Knight(Color.white, Position.of(4, 4));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 8);
    }

    @Test
    void shouldReturn2PositionsForKnightInCorner() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);
        Knight knight = new Knight(Color.white, Position.of(1, 1));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 2);
    }

    @Test
    void shouldReturn4PositionsForKnightOnMiddleOfEdge() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);
        Knight knight = new Knight(Color.white, Position.of(8, 4));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 4);
    }

    @Test
    void shouldReturn3PositionsForKnightOnEdgeCloseToCorner() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);
        Knight knight = new Knight(Color.white, Position.of(8, 2));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 3);
    }

    @Test
    void shouldReturn8PositionsWhenAttackEnemyPieceFromMiddle() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(3, 2)));
        Knight knight = new Knight(Color.white, Position.of(4, 4));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 8);
    }

    @Disabled //TODO
    @Test
    void shouldReturn7PositionsWhenTryToGoBackToFieldWithPiece() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(3, 2)));
        Knight knight = new Knight(Color.white, Position.of(4, 4));
        assertEquals(knight.getPossibleStartPositions(chessBoard).size(), 7);
    }

    @Test
    void shouldReturn7PositionsWhenAttackOwnPieceFromMiddle() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.white, Position.of(3, 2)));
        Knight knight = new Knight(Color.white, Position.of(4, 4));
        assertEquals(knight.getPossibleEndPositions(chessBoard).size(), 7);
    }
}