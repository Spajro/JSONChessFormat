package data.model;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import data.model.diagrams.Diagram;
import data.model.games.GamesUpdateEvent;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;

public class DiagramController {

    public record Result(Diagram diagram, GamesUpdateEvent event) {
    }

    public Result makeMove(Diagram diagram, RawMove move, PromotionTypeProvider typeProvider) {
        ChessBoard chessBoard = diagram.getBoard();
        MoveResult moveResult = chessBoard.makeMove(move);
        Optional<ValidMoveResult> validMoveResult = moveResult.validate(typeProvider);

        if (validMoveResult.isEmpty()) {
            Log.log().warn("Illegal Move");
            return new Result(diagram, GamesUpdateEvent.empty());
        }
        GamesUpdateEvent gamesUpdateEvent;
        if (diagram.isLazy()) {
            gamesUpdateEvent = expand(diagram);
        } else {
            gamesUpdateEvent = GamesUpdateEvent.empty();
        }

        for (Diagram diagram1 : diagram.getNextDiagrams()) {
            if (diagram1.getCreatingMove().isPresent() && diagram1.getCreatingMove().get().equals(move)) {
                return new Result(diagram1, gamesUpdateEvent);
            }
        }

        Diagram nextDiagram = new Diagram(move, chessBoard, diagram);
        diagram.getNextDiagrams().add(nextDiagram);
        return new Result(nextDiagram, gamesUpdateEvent);
    }

    public GamesUpdateEvent insert(Diagram tree, ArrayDeque<RawMove> moves, MetaData metaData) {
        if (moves.isEmpty()) {
            tree.getMetaData().add(metaData);
            return GamesUpdateEvent.of(metaData, tree);
        }

        if (tree.isLazy()) {
            if (tree.getLazyMovesList().isEmpty()) {
                tree.setLazyMoves(moves);
                tree.getMetaData().add(metaData);
                return GamesUpdateEvent.of(metaData, tree);
            }

            if (moves.stream().toList().equals(tree.getLazyMovesList())) {
                tree.getMetaData().add(metaData);
                return GamesUpdateEvent.of(metaData, tree);
            }
        }

        RawMove move = moves.poll();

        if (tree.isLazy() && tree.getLazyMovesList().get(0).equals(move)) {
            GamesUpdateEvent event = expand(tree);
            Diagram diagram = tree.getNextDiagrams().get(0);
            return event.join(insert(diagram, moves, metaData));
        }

        if (!tree.isLazy()) {
            for (Diagram nextDiagram : tree.getNextDiagrams()) {
                if (nextDiagram.getCreatingMove().isPresent()) {
                    if (nextDiagram.getCreatingMove().get().equals(move)) {
                        return insert(nextDiagram, moves, metaData);
                    }
                }
            }
        }

        Diagram diagram = new Diagram(
                move,
                tree.getBoard(),
                tree,
                moves
        );
        GamesUpdateEvent event;
        if (tree.isLazy()) {
            event = expand(tree);
        } else {
            event = GamesUpdateEvent.empty();
        }
        tree.getNextDiagrams().add(diagram);
        diagram.getMetaData().add(metaData);

        return event.join(GamesUpdateEvent.of(metaData, diagram));
    }

    public GamesUpdateEvent expand(Diagram diagram) {
        RawMove move = diagram.getLazyMovesDeque().poll();
        if (move == null) {
            diagram.expandNextDiagrams();
            diagram.setLazyMoves(null);
            return GamesUpdateEvent.empty();
        }

        ChessBoard chessBoard = diagram.getBoard();
        Optional<ValidMoveResult> validMoveResult = chessBoard.makeMove(move).validate();
        if (validMoveResult.isEmpty()) {
            throw new IllegalStateException();
        }

        Diagram lazy = new Diagram(
                move,
                chessBoard,
                diagram,
                diagram.getLazyMovesDeque()
        );
        diagram.expandNextDiagrams();
        diagram.setLazyMoves(null);
        diagram.getNextDiagrams().add(lazy);
        return moveMetaData(diagram, lazy);
    }

    private GamesUpdateEvent moveMetaData(Diagram from, Diagram to) {
        int depth = from.depth();
        List<MetaData> gameData = from.getMetaData()
                .stream()
                .filter(metaData -> !(metaData instanceof GameData) || ((GameData) metaData).length() != depth)
                .toList();
        GamesUpdateEvent event = GamesUpdateEvent.of(gameData, to);
        to.getMetaData().addAll(gameData);
        from.getMetaData().removeAll(gameData);
        return event;
    }
}
