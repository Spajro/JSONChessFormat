package data.model;

import chess.Position;
import chess.board.lowlevel.Board;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;
import chess.moves.valid.executable.SimpleMove;
import data.model.games.GamesUpdateEvent;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagramManagerTest {

    @Test
    void insertIntoEmptyTreeTest() {
        Diagram tree = new Diagram();
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        ExecutableMove executable1 = new SimpleMove(raw1, Board.getStart());
        ExecutableMove executable2 = new SimpleMove(raw2, executable1.makeMove());
        ArrayDeque<ExecutableMove> moves = new ArrayDeque<>(List.of(executable1, executable2));
        MetaData metaData = new GameData("event", "site", "date", "round", "white", "black", "result", 2);

        GamesUpdateEvent event = new DiagramManager().insert(tree, moves, metaData);


        assertEquals(1, tree.getNextDiagrams().size());
        Diagram diagram = tree.getNextDiagrams().get(0);

        assertTrue(diagram.getCreatingMove().isPresent());
        assertEquals(raw1, diagram.getCreatingMove().get());

        assertTrue(diagram.isLazy());
        assertEquals(1, diagram.getLazyMoves().size());
        assertEquals(raw2, diagram.getLazyMoves().get(0));

        assertEquals(diagram, event.gamesMap().get(metaData));
        assertTrue(diagram.getMetaData().contains(metaData));

    }

    @Test
    void insertEmptyGameIntoEmptyTreeTest() {
        Diagram tree = new Diagram();
        ArrayDeque<ExecutableMove> moves = new ArrayDeque<>();
        MetaData metaData = new GameData("event", "site", "date", "round", "white", "black", "result", 0);

        GamesUpdateEvent event = new DiagramManager().insert(tree, moves, metaData);

        assertNull(tree.getLazyMoves());
        assertTrue(tree.getNextDiagrams().isEmpty());
        assertEquals(tree, event.gamesMap().get(metaData));
        assertTrue(tree.getMetaData().contains(metaData));
    }

    @Test
    void insert2GamesDifferentAt1MoveTest() {
        Diagram tree = new Diagram();
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(5, 2), Position.of(5, 4));
        RawMove raw3 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        ExecutableMove executable1 = new SimpleMove(raw1, Board.getStart());
        ExecutableMove executable2 = new SimpleMove(raw2, Board.getStart());
        ExecutableMove executable3 = new SimpleMove(raw3, executable1.makeMove());
        ExecutableMove executable4 = new SimpleMove(raw3, executable2.makeMove());
        ArrayDeque<ExecutableMove> moves1 = new ArrayDeque<>(List.of(executable1, executable3));
        ArrayDeque<ExecutableMove> moves2 = new ArrayDeque<>(List.of(executable2, executable4));
        MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
        MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

        GamesUpdateEvent event1 = new DiagramManager().insert(tree, moves1, metaData1);
        GamesUpdateEvent event2 = new DiagramManager().insert(tree, moves2, metaData2);

        assertFalse(tree.isLazy());
        assertEquals(2, tree.getNextDiagrams().size());


        Diagram diagram1 = tree.getNextDiagrams().get(0);
        assertTrue(diagram1.getCreatingMove().isPresent());
        assertEquals(raw1, diagram1.getCreatingMove().get());

        assertTrue(diagram1.isLazy());
        assertEquals(1, diagram1.getLazyMoves().size());
        assertEquals(raw3, diagram1.getLazyMoves().get(0));

        Diagram diagram2 = tree.getNextDiagrams().get(1);
        assertTrue(diagram2.getCreatingMove().isPresent());
        assertEquals(raw2, diagram2.getCreatingMove().get());

        assertTrue(diagram2.isLazy());
        assertEquals(1, diagram2.getLazyMoves().size());
        assertEquals(raw3, diagram2.getLazyMoves().get(0));

        assertEquals(diagram1, event1.gamesMap().get(metaData1));

        assertEquals(diagram2, event2.gamesMap().get(metaData2));

        assertTrue(diagram1.getMetaData().contains(metaData1));
        assertTrue(diagram2.getMetaData().contains(metaData2));
    }

    @Test
    void insert2GamesDifferentAt2MoveTest() {
        Diagram tree = new Diagram();
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
        RawMove raw3 = RawMove.of(Position.of(5, 7), Position.of(5, 5));
        RawMove raw4 = RawMove.of(Position.of(5, 2), Position.of(5, 4));
        ExecutableMove executable1 = new SimpleMove(raw1, Board.getStart());
        ExecutableMove executable2 = new SimpleMove(raw2, executable1.makeMove());
        ExecutableMove executable3 = new SimpleMove(raw3, executable1.makeMove());
        ExecutableMove executable4 = new SimpleMove(raw4, executable2.makeMove());
        ExecutableMove executable5 = new SimpleMove(raw4, executable3.makeMove());
        ArrayDeque<ExecutableMove> moves1 = new ArrayDeque<>(List.of(executable1, executable2, executable4));
        ArrayDeque<ExecutableMove> moves2 = new ArrayDeque<>(List.of(executable1, executable3, executable5));
        MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
        MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

        GamesUpdateEvent event1 = new DiagramManager().insert(tree, moves1, metaData1);
        GamesUpdateEvent event2 = new DiagramManager().insert(tree, moves2, metaData2);

        assertFalse(tree.isLazy());
        assertEquals(1, tree.getNextDiagrams().size());

        Diagram diagram = tree.getNextDiagrams().get(0);
        assertTrue(diagram.getCreatingMove().isPresent());
        assertEquals(raw1, diagram.getCreatingMove().get());
        assertEquals(2, diagram.getNextDiagrams().size());

        Diagram diagram1 = diagram.getNextDiagrams().get(0);
        assertTrue(diagram1.getCreatingMove().isPresent());
        assertEquals(raw2, diagram1.getCreatingMove().get());

        assertTrue(diagram1.isLazy());
        assertEquals(1, diagram1.getLazyMoves().size());
        assertEquals(raw4, diagram1.getLazyMoves().get(0));

        Diagram diagram2 = diagram.getNextDiagrams().get(1);
        assertTrue(diagram2.getCreatingMove().isPresent());
        assertEquals(raw3, diagram2.getCreatingMove().get());

        assertTrue(diagram2.isLazy());
        assertEquals(1, diagram2.getLazyMoves().size());
        assertEquals(raw4, diagram2.getLazyMoves().get(0));

        assertEquals(diagram, event1.gamesMap().get(metaData1));

        assertEquals(diagram1, event2.gamesMap().get(metaData1));
        assertEquals(diagram2, event2.gamesMap().get(metaData2));

        assertTrue(diagram1.getMetaData().contains(metaData1));
        assertTrue(diagram2.getMetaData().contains(metaData2));
    }

    @Test
    void insert2GamesWithSecondHavingOneMoreMoveTest() {
        Diagram tree = new Diagram();
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(5, 7), Position.of(5, 5));
        ExecutableMove executable1 = new SimpleMove(raw1, Board.getStart());
        ExecutableMove executable2 = new SimpleMove(raw2, executable1.makeMove());
        ArrayDeque<ExecutableMove> moves1 = new ArrayDeque<>(List.of(executable1));
        ArrayDeque<ExecutableMove> moves2 = new ArrayDeque<>(List.of(executable1, executable2));
        MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 1);
        MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

        GamesUpdateEvent event1 = new DiagramManager().insert(tree, moves1, metaData1);
        GamesUpdateEvent event2 = new DiagramManager().insert(tree, moves2, metaData2);

        assertFalse(tree.isLazy());
        assertEquals(1, tree.getNextDiagrams().size());

        Diagram diagram = tree.getNextDiagrams().get(0);
        assertTrue(diagram.getCreatingMove().isPresent());
        assertEquals(raw1, diagram.getCreatingMove().get());

        assertTrue(diagram.isLazy());
        assertEquals(1, diagram.getLazyMoves().size());
        assertEquals(raw2, diagram.getLazyMoves().get(0));

        assertEquals(diagram, event1.gamesMap().get(metaData1));

        assertEquals(diagram, event2.gamesMap().get(metaData2));

        assertTrue(diagram.getMetaData().contains(metaData2));
    }

    @Test
    void insert2GamesWithFirstHavingOneMoreMoveTest() {
        Diagram tree = new Diagram();
        RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
        RawMove raw2 = RawMove.of(Position.of(5, 7), Position.of(5, 5));
        ExecutableMove executable1 = new SimpleMove(raw1, Board.getStart());
        ExecutableMove executable2 = new SimpleMove(raw2, executable1.makeMove());
        ArrayDeque<ExecutableMove> moves1 = new ArrayDeque<>(List.of(executable1, executable2));
        ArrayDeque<ExecutableMove> moves2 = new ArrayDeque<>(List.of(executable1));
        MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
        MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 1);

        GamesUpdateEvent event1 = new DiagramManager().insert(tree, moves1, metaData1);
        GamesUpdateEvent event2 = new DiagramManager().insert(tree, moves2, metaData2);

        assertFalse(tree.isLazy());
        assertEquals(1, tree.getNextDiagrams().size());

        Diagram diagram = tree.getNextDiagrams().get(0);
        assertTrue(diagram.getCreatingMove().isPresent());
        assertEquals(raw1, diagram.getCreatingMove().get());

        assertTrue(diagram.isLazy());
        assertEquals(1, diagram.getLazyMoves().size());
        assertEquals(raw2, diagram.getLazyMoves().get(0));

        assertEquals(diagram, event1.gamesMap().get(metaData1));

        assertEquals(diagram, event2.gamesMap().get(metaData2));

        assertTrue(diagram.getMetaData().contains(metaData1));
        assertTrue(diagram.getMetaData().contains(metaData2));
    }
}