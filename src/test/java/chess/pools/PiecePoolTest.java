package chess.pools;

import chess.Position;
import chess.color.Color;
import chess.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PiecePoolTest {

    @Test
    void shouldGetWhitePawnAt11Test() {
        Piece expected = new Pawn(Color.white, Position.of(1, 1));
        Piece actual = new PiecePool().get(Position.of(1, 1), Color.white, Piece.Type.PAWN);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    void shouldGetBlackKnightAt23Test() {
        Piece expected = new Knight(Color.black, Position.of(2, 3));
        Piece actual = new PiecePool().get(Position.of(2, 3), Color.black, Piece.Type.KNIGHT);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    void shouldGetWhiteBishopAt88Test() {
        Piece expected = new Bishop(Color.white, Position.of(8, 8));
        Piece actual = new PiecePool().get(Position.of(8, 8), Color.white, Piece.Type.BISHOP);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    void shouldGetBlackRookAt44Test() {
        Piece expected = new Rook(Color.black, Position.of(4, 4));
        Piece actual = new PiecePool().get(Position.of(4, 4), Color.black, Piece.Type.ROOK);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    void shouldGetWhiteQueenAt81Test() {
        Piece expected = new Queen(Color.white, Position.of(8, 1));
        Piece actual = new PiecePool().get(Position.of(8, 1), Color.white, Piece.Type.QUEEN);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

    @Test
    void shouldGetBlackKingAt37Test() {
        Piece expected = new King(Color.black, Position.of(3, 7));
        Piece actual = new PiecePool().get(Position.of(3, 7), Color.black, Piece.Type.KING);

        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getType(), actual.getType());
    }

}