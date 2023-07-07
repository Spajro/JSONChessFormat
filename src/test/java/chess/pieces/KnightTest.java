package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void shouldReturn8PositionsForKnightInMiddle(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);
        Knight knight=new Knight(Color.white, Position.of(4,4),chessBoard);
        assertEquals(knight.getPossibleEndPositions().size(),8);
        assertEquals(knight.getPossibleStartPositions().size(),8);
    }

    @Test
    void shouldReturn2PositionsForKnightInCorner(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);
        Knight knight=new Knight(Color.white, Position.of(1,1),chessBoard);
        assertEquals(knight.getPossibleEndPositions().size(),2);
        assertEquals(knight.getPossibleStartPositions().size(),2);
    }

    @Test
    void shouldReturn4PositionsForKnightOnMiddleOfEdge(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);
        Knight knight=new Knight(Color.white, Position.of(8,4),chessBoard);
        assertEquals(knight.getPossibleEndPositions().size(),4);
        assertEquals(knight.getPossibleStartPositions().size(),4);
    }

    @Test
    void shouldReturn3PositionsForKnightOnEdgeCloseToCorner(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);
        Knight knight=new Knight(Color.white, Position.of(8,2),chessBoard);
        assertEquals(knight.getPossibleEndPositions().size(),3);
        assertEquals(knight.getPossibleStartPositions().size(),3);
    }

    @Test
    void shouldReturn8PositionsWhenAttackEnemyPieceFromMiddle(){
        ChessBoard chessBoard1=ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2=chessBoard1.put(new Pawn(Color.black, Position.of(3,2),chessBoard1));
        Knight knight=new Knight(Color.white, Position.of(4,4),chessBoard2);
        assertEquals(knight.getPossibleEndPositions().size(),8);
    }

    @Test
    void shouldReturn7PositionsWhenTryToGoBackToFieldWithPiece(){
        ChessBoard chessBoard1=ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2=chessBoard1.put(new Pawn(Color.black, Position.of(3,2),chessBoard1));
        Knight knight=new Knight(Color.white, Position.of(4,4),chessBoard2);
        assertEquals(knight.getPossibleStartPositions().size(),7);
    }

    @Test
    void shouldReturn7PositionsWhenAttackOwnPieceFromMiddle(){
        ChessBoard chessBoard1=ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2=chessBoard1.put(new Pawn(Color.white, Position.of(3,2),chessBoard1));
        Knight knight=new Knight(Color.white, Position.of(4,4),chessBoard2);
        assertEquals(knight.getPossibleEndPositions().size(),7);
    }
}