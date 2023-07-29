package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void shouldReturn2PositionsFromMiddleOfStartingRow() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Pawn pawn = new Pawn(Color.white, Position.of(5, 2));
        Set<Position> positionSet = pawn.getPossibleEndPositions(chessBoard);

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(Position.of(5, 3), Position.of(5, 4))));
    }

    @Test
    void shouldReturn2PositionsFromEdgeOfStartingRow() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Pawn pawn = new Pawn(Color.white, Position.of(1, 2));
        Set<Position> positionSet = pawn.getPossibleEndPositions(chessBoard);

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                Position.of(1, 3),
                Position.of(1, 4)
        )));
    }

    @Test
    void shouldReturn0PositionsWhenPieceAhead() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.white, Position.of(5, 5)));

        Pawn pawn = new Pawn(Color.white, Position.of(5, 4));
        Set<Position> positionSet = pawn.getPossibleEndPositions(chessBoard);

        assertEquals(0, positionSet.size());
    }

    @Test
    void shouldReturn2PositionsWhenCanBeatLeft() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(4, 5)));

        Pawn pawn = new Pawn(Color.white, Position.of(5, 4));
        Set<Position> positionSet = pawn.getPossibleEndPositions(chessBoard);

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                Position.of(4, 5),
                Position.of(5, 5)
        )));
    }

    @Test
    void shouldReturn2PositionsWhenCanBeatRight() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(6, 5)));

        Pawn pawn = new Pawn(Color.white, Position.of(5, 4));
        Set<Position> positionSet = pawn.getPossibleEndPositions(chessBoard);

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                Position.of(5, 5),
                Position.of(6, 5)
        )));
    }

    @Disabled //TODO
    @Test
    void shouldReturn2PositionsWhenTryToGoBackToFieldWithPiece() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white).put(new Pawn(Color.black, Position.of(5, 3)));

        Pawn pawn = new Pawn(Color.white, Position.of(5, 4));
        Set<Position> positionSet = pawn.getPossibleStartPositions(chessBoard);

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                Position.of(4, 3),
                Position.of(6, 3)
        )));
    }

    @Test
    void shouldReturn0PositionsWhenBlockedOnStartRow() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white)
                .put(new Pawn(Color.white, Position.of(2, 2)))
                .put(new Pawn(Color.white, Position.of(2, 3)));
        assertEquals(0, new Pawn(Color.white, Position.of(2, 2)).getPossibleEndPositions(chessBoard).size());
    }
}