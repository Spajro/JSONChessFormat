package data.model;

import chess.Position;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class DiagramTest {

    @Test
    void getChessBoardTest() {
        RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        Diagram diagram = new Diagram(
                raw,
                new ChessBoard(),
                new Diagram(),
                new ArrayDeque<>()
        );
        ChessBoard expected = new ChessBoard().makeMove(raw).validate().orElseThrow().getResult();

        ChessBoard actual = diagram.getBoard();

        assertEquals(expected, actual);
    }

    @Test
    void getChessBoardRootTest() {
        assertEquals(new Diagram().getBoard(), new ChessBoard());
    }

    @Test
    void depthTest() {
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        Diagram root = new Diagram();
        Diagram son = new DiagramController().makeMove(root, raw1, null).diagram();
        Diagram grandson = new DiagramController().makeMove(son, raw2, null).diagram();

        assertEquals(0, root.depth());
        assertEquals(1, son.depth());
        assertEquals(2, grandson.depth());
    }

    @Test
    void getRootTest() {
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        Diagram root = new Diagram();
        Diagram son = new DiagramController().makeMove(root, raw1, null).diagram();
        Diagram grandson = new DiagramController().makeMove(son, raw2, null).diagram();

        assertEquals(root, root.getRoot());
        assertEquals(root, son.getRoot());
        assertEquals(root, grandson.getRoot());
    }
}