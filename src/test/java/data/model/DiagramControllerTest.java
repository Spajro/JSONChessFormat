package data.model;

import chess.Position;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import data.model.games.GamesUpdateEvent;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagramControllerTest {

    @Nested
    class MakeMoveTests {
        @Test
        void makeMoveTest() {
            RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            Diagram root = new Diagram();

            Diagram actual = new DiagramController().makeMove(root, raw, null).diagram();

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

            Diagram diagram1 = new DiagramController().makeMove(root, raw, null).diagram();
            Diagram diagram2 = new DiagramController().makeMove(root, raw, null).diagram();


            assertEquals(1, root.getNextDiagrams().size());
            Diagram diagram = root.getNextDiagrams().get(0);

            assertEquals(diagram, diagram1);
            assertEquals(diagram, diagram2);
        }

        @Test
        void invalidMakeMoveTest() {
            RawMove raw = RawMove.of(Position.of(4, 1), Position.of(4, 4));
            Diagram root = new Diagram();

            Diagram actual = new DiagramController().makeMove(root, raw, null).diagram();

            assertEquals(root, actual);
        }
    }

    @Nested
    class InsertTests {
        @Test
        void insertIntoEmptyTreeTest() {
            Diagram tree = new Diagram();
            RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
            ArrayDeque<RawMove> moves = new ArrayDeque<>(List.of(raw1, raw2));
            MetaData metaData = new GameData("event", "site", "date", "round", "white", "black", "result", 2);

            GamesUpdateEvent event = new DiagramController().insert(tree, moves, metaData);


            assertEquals(1, tree.getNextDiagrams().size());
            Diagram diagram = tree.getNextDiagrams().get(0);

            assertTrue(diagram.getCreatingMove().isPresent());
            assertEquals(raw1, diagram.getCreatingMove().get());

            assertTrue(diagram.isLazy());
            assertEquals(1, diagram.getLazyMovesList().size());
            assertEquals(raw2, diagram.getLazyMovesList().get(0));

            assertEquals(diagram, event.gamesMap().get(metaData));
            assertTrue(diagram.getMetaData().contains(metaData));

        }

        @Test
        void insertEmptyGameIntoEmptyTreeTest() {
            Diagram tree = new Diagram();
            ArrayDeque<RawMove> moves = new ArrayDeque<>();
            MetaData metaData = new GameData("event", "site", "date", "round", "white", "black", "result", 0);

            GamesUpdateEvent event = new DiagramController().insert(tree, moves, metaData);

            assertNull(tree.getLazyMovesList());
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
            ArrayDeque<RawMove> moves1 = new ArrayDeque<>(List.of(raw1, raw3));
            ArrayDeque<RawMove> moves2 = new ArrayDeque<>(List.of(raw2, raw3));
            MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
            MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

            GamesUpdateEvent event1 = new DiagramController().insert(tree, moves1, metaData1);
            GamesUpdateEvent event2 = new DiagramController().insert(tree, moves2, metaData2);

            assertFalse(tree.isLazy());
            assertEquals(2, tree.getNextDiagrams().size());


            Diagram diagram1 = tree.getNextDiagrams().get(0);
            assertTrue(diagram1.getCreatingMove().isPresent());
            assertEquals(raw1, diagram1.getCreatingMove().get());

            assertTrue(diagram1.isLazy());
            assertEquals(1, diagram1.getLazyMovesList().size());
            assertEquals(raw3, diagram1.getLazyMovesList().get(0));

            Diagram diagram2 = tree.getNextDiagrams().get(1);
            assertTrue(diagram2.getCreatingMove().isPresent());
            assertEquals(raw2, diagram2.getCreatingMove().get());

            assertTrue(diagram2.isLazy());
            assertEquals(1, diagram2.getLazyMovesList().size());
            assertEquals(raw3, diagram2.getLazyMovesList().get(0));

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
            ArrayDeque<RawMove> moves1 = new ArrayDeque<>(List.of(raw1, raw2, raw4));
            ArrayDeque<RawMove> moves2 = new ArrayDeque<>(List.of(raw1, raw3, raw4));
            MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
            MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

            GamesUpdateEvent event1 = new DiagramController().insert(tree, moves1, metaData1);
            GamesUpdateEvent event2 = new DiagramController().insert(tree, moves2, metaData2);

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
            assertEquals(1, diagram1.getLazyMovesList().size());
            assertEquals(raw4, diagram1.getLazyMovesList().get(0));

            Diagram diagram2 = diagram.getNextDiagrams().get(1);
            assertTrue(diagram2.getCreatingMove().isPresent());
            assertEquals(raw3, diagram2.getCreatingMove().get());

            assertTrue(diagram2.isLazy());
            assertEquals(1, diagram2.getLazyMovesList().size());
            assertEquals(raw4, diagram2.getLazyMovesList().get(0));

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
            ArrayDeque<RawMove> moves1 = new ArrayDeque<>(List.of(raw1));
            ArrayDeque<RawMove> moves2 = new ArrayDeque<>(List.of(raw1, raw2));
            MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 1);
            MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

            GamesUpdateEvent event1 = new DiagramController().insert(tree, moves1, metaData1);
            GamesUpdateEvent event2 = new DiagramController().insert(tree, moves2, metaData2);

            assertFalse(tree.isLazy());
            assertEquals(1, tree.getNextDiagrams().size());

            Diagram diagram = tree.getNextDiagrams().get(0);
            assertTrue(diagram.getCreatingMove().isPresent());
            assertEquals(raw1, diagram.getCreatingMove().get());

            assertTrue(diagram.isLazy());
            assertEquals(1, diagram.getLazyMovesList().size());
            assertEquals(raw2, diagram.getLazyMovesList().get(0));

            assertEquals(diagram, event1.gamesMap().get(metaData1));

            assertEquals(diagram, event2.gamesMap().get(metaData2));

            assertTrue(diagram.getMetaData().contains(metaData2));
        }

        @Test
        void insert2GamesWithFirstHavingOneMoreMoveTest() {
            Diagram tree = new Diagram();
            RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            RawMove raw2 = RawMove.of(Position.of(5, 7), Position.of(5, 5));
            ArrayDeque<RawMove> moves1 = new ArrayDeque<>(List.of(raw1, raw2));
            ArrayDeque<RawMove> moves2 = new ArrayDeque<>(List.of(raw1));
            MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
            MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 1);

            GamesUpdateEvent event1 = new DiagramController().insert(tree, moves1, metaData1);
            GamesUpdateEvent event2 = new DiagramController().insert(tree, moves2, metaData2);

            assertFalse(tree.isLazy());
            assertEquals(1, tree.getNextDiagrams().size());

            Diagram diagram = tree.getNextDiagrams().get(0);
            assertTrue(diagram.getCreatingMove().isPresent());
            assertEquals(raw1, diagram.getCreatingMove().get());

            assertTrue(diagram.isLazy());
            assertEquals(1, diagram.getLazyMovesList().size());
            assertEquals(raw2, diagram.getLazyMovesList().get(0));

            assertEquals(diagram, event1.gamesMap().get(metaData1));

            assertEquals(diagram, event2.gamesMap().get(metaData2));

            assertTrue(diagram.getMetaData().contains(metaData1));
            assertTrue(diagram.getMetaData().contains(metaData2));
        }

        @Test
        void insert2IdenticalGamesTest() {
            Diagram tree = new Diagram();
            RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
            RawMove raw3 = RawMove.of(Position.of(5, 2), Position.of(5, 4));
            ArrayDeque<RawMove> moves1 = new ArrayDeque<>(List.of(raw1, raw2, raw3));
            ArrayDeque<RawMove> moves2 = new ArrayDeque<>(List.of(raw1, raw2, raw3));
            MetaData metaData1 = new GameData("event1", "site", "date", "round", "white", "black", "result", 2);
            MetaData metaData2 = new GameData("event2", "site", "date", "round", "white", "black", "result", 2);

            GamesUpdateEvent event1 = new DiagramController().insert(tree, moves1, metaData1);
            GamesUpdateEvent event2 = new DiagramController().insert(tree, moves2, metaData2);

            assertFalse(tree.isLazy());
            assertEquals(1, tree.getNextDiagrams().size());

            Diagram diagram = tree.getNextDiagrams().get(0);
            assertTrue(diagram.getCreatingMove().isPresent());
            assertEquals(raw1, diagram.getCreatingMove().get());

            assertTrue(diagram.isLazy());
            assertEquals(2, diagram.getLazyMovesList().size());
            assertEquals(raw2, diagram.getLazyMovesList().get(0));
            assertEquals(raw3, diagram.getLazyMovesList().get(1));

            assertEquals(diagram, event1.gamesMap().get(metaData1));

            assertEquals(diagram, event2.gamesMap().get(metaData2));

            assertTrue(diagram.getMetaData().contains(metaData1));
            assertTrue(diagram.getMetaData().contains(metaData2));
        }
    }

    @Nested
    class ExpandTests {
        @Test
        void expandTest() {
            RawMove raw1 = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            RawMove raw2 = RawMove.of(Position.of(4, 7), Position.of(4, 5));
            RawMove raw3 = RawMove.of(Position.of(5, 2), Position.of(5, 4));
            Diagram diagram = new Diagram(
                    raw1,
                    new ChessBoard(),
                    new Diagram(),
                    new ArrayDeque<>(List.of(
                            raw2,
                            raw3))
            );

            GamesUpdateEvent event = new DiagramController().expand(diagram);//TODO

            assertFalse(diagram.isLazy());
            assertEquals(1, diagram.getNextDiagrams().size());
            Diagram expanded = diagram.getNextDiagrams().get(0);

            assertTrue(expanded.getCreatingMove().isPresent());
            assertEquals(raw2, expanded.getCreatingMove().get());

            assertTrue(expanded.isLazy());
            assertEquals(1, expanded.getLazyMovesList().size());
            assertEquals(raw3, expanded.getLazyMovesList().get(0));
        }

        @Test
        void expandEmptyLazyMovesTest() {
            RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            Diagram diagram = new Diagram(
                    raw,
                    new ChessBoard(),
                    new Diagram(),
                    new ArrayDeque<>()
            );

            GamesUpdateEvent event = new DiagramController().expand(diagram);//TODO

            assertFalse(diagram.isLazy());
            assertEquals(0, diagram.getNextDiagrams().size());
        }

        @Test
        void expandIllegalMoveShouldThrowTest() {
            RawMove raw = RawMove.of(Position.of(4, 2), Position.of(4, 4));
            Diagram diagram = new Diagram(
                    raw,
                    new ChessBoard(),
                    new Diagram(),
                    new ArrayDeque<>(List.of(raw))
            );

            assertThrows(IllegalStateException.class, () -> new DiagramController().expand(diagram));
        }
    }
}