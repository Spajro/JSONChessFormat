package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void shouldReturn2PositionsFromMiddleOfStartingRow() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Pawn pawn = new Pawn(Color.white, new Position(5, 2), chessBoard);
        Set<Position> positionSet = pawn.getPossibleEndPositions();

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(new Position(5, 3), new Position(5, 4))));
    }

    @Test
    void shouldReturn2PositionsFromEdgeOfStartingRow() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Pawn pawn = new Pawn(Color.white, new Position(1, 2), chessBoard);
        Set<Position> positionSet = pawn.getPossibleEndPositions();

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                new Position(1, 3),
                new Position(1, 4)
        )));
    }

    @Test
    void shouldReturn0PositionsWhenPieceAhead() {
        ChessBoard chessBoard1 = ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2 = chessBoard1.put(new Pawn(Color.white, new Position(5, 5), chessBoard1));

        Pawn pawn = new Pawn(Color.white, new Position(5, 4), chessBoard2);
        Set<Position> positionSet = pawn.getPossibleEndPositions();

        assertEquals(0, positionSet.size());
    }

    @Test
    void shouldReturn2PositionsWhenCanBeatLeft() {
        ChessBoard chessBoard1 = ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2 = chessBoard1.put(new Pawn(Color.black, new Position(4, 5), chessBoard1));

        Pawn pawn = new Pawn(Color.white, new Position(5, 4), chessBoard2);
        Set<Position> positionSet = pawn.getPossibleEndPositions();

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                new Position(4, 5),
                new Position(5, 5)
        )));
    }

    @Test
    void shouldReturn2PositionsWhenCanBeatRight() {
        ChessBoard chessBoard1 = ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2 = chessBoard1.put(new Pawn(Color.black, new Position(6, 5), chessBoard1));

        Pawn pawn = new Pawn(Color.white, new Position(5, 4), chessBoard2);
        Set<Position> positionSet = pawn.getPossibleEndPositions();

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                new Position(5, 5),
                new Position(6, 5)
        )));
    }

    @Test
    void shouldReturn4PositionsWhenTryToGoBack() {
        ChessBoard chessBoard = ChessBoard.getBlank(Color.white);

        Pawn pawn = new Pawn(Color.white, new Position(5, 4), chessBoard);
        Set<Position> positionSet = pawn.getPossibleStartPositions();

        assertEquals(4, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                new Position(5, 2),
                new Position(5, 3),
                new Position(4, 3),
                new Position(6, 3)
        )));
    }

    @Test
    void shouldReturn2PositionsWhenTryToGoBackToFieldWithPiece() {
        ChessBoard chessBoard1 = ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2 = chessBoard1.put(new Pawn(Color.black, new Position(5, 3), chessBoard1));

        Pawn pawn = new Pawn(Color.white, new Position(5, 4), chessBoard2);
        Set<Position> positionSet = pawn.getPossibleStartPositions();

        assertEquals(2, positionSet.size());
        assertTrue(positionSet.containsAll(Set.of(
                new Position(4, 3),
                new Position(6, 3)
        )));
    }

    @Test
    void shouldReturn0PositionsWhenBlockedOnStartRow(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white)
                .put(new Pawn(Color.white,new Position(2,2),null))
                .put(new Pawn(Color.white,new Position(2,3),null));
        assertEquals(0,new Pawn(Color.white,new Position(2,2),chessBoard).getPossibleEndPositions().size());
    }
}