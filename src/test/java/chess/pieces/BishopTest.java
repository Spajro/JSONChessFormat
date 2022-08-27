package chess.pieces;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void shouldReturn7PositionsForBishopInCorner(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);

        Bishop bishop=new Bishop(Color.white,new Position(1,1),chessBoard);

        assertEquals(7,bishop.getPossibleEndPositions().size());
        assertEquals(7,bishop.getPossibleStartPositions().size());
    }

    @Test
    void shouldReturn13PositionsForBishopInMiddle(){
        ChessBoard chessBoard=ChessBoard.getBlank(Color.white);

        Bishop bishop=new Bishop(Color.white,new Position(4,4),chessBoard);

        assertEquals(13,bishop.getPossibleEndPositions().size());
        assertEquals(13,bishop.getPossibleStartPositions().size());
    }

    @Test
    void shouldReturn1PositionsForBlockedBishopInCorner(){
        ChessBoard chessBoard1=ChessBoard.getBlank(Color.white);
        ChessBoard chessBoard2=chessBoard1.put(new Pawn(Color.black,new Position(2,2),chessBoard1));

        Bishop bishop=new Bishop(Color.white,new Position(1,1),chessBoard2);

        assertEquals(1,bishop.getPossibleEndPositions().size());
        assertEquals(0,bishop.getPossibleStartPositions().size());
    }
}