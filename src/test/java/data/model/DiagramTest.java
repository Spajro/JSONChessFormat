package data.model;

import chess.Position;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

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
    void makeMoveTest() {
        RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        Diagram root = new Diagram();

        Diagram actual = root.makeMove(raw, null);

        assertEquals(1, root.getNextDiagrams().size());
        Diagram diagram = root.getNextDiagrams().get(0);
        assertEquals(diagram, actual);

        assertTrue(actual.getCreatingMove().isPresent());
        assertEquals(raw, actual.getCreatingMove().get());

        assertTrue(actual.getParent().isPresent());
        assertEquals(root, actual.getParent().get());
    }

    @Test
    void makeMoveTwiceTest() {
        RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        Diagram root = new Diagram();

        Diagram diagram1 = root.makeMove(raw, null);
        Diagram diagram2 = root.makeMove(raw, null);


        assertEquals(1, root.getNextDiagrams().size());
        Diagram diagram = root.getNextDiagrams().get(0);

        assertEquals(diagram, diagram1);
        assertEquals(diagram, diagram2);
    }

    @Test
    void invalidMakeMoveTest() {
        RawMove raw = RawMove.of(Position.of(4, 1), Position.of(4, 4));
        Diagram root = new Diagram();

        Diagram actual = root.makeMove(raw, null);

        assertEquals(root, actual);
    }

    @Test
    void depthTest() {
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        Diagram root = new Diagram();
        Diagram son = root.makeMove(raw1, null);
        Diagram grandson = son.makeMove(raw2, null);

        assertEquals(0, root.depth());
        assertEquals(1, son.depth());
        assertEquals(2, grandson.depth());
    }

    @Test
    void getRootTest() {
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        Diagram root = new Diagram();
        Diagram son = root.makeMove(raw1, null);
        Diagram grandson = son.makeMove(raw2, null);

        assertEquals(root, root.getRoot());
        assertEquals(root, son.getRoot());
        assertEquals(root, grandson.getRoot());
    }
}