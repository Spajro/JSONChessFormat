package data.model;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.results.ValidMoveResult;
import data.model.games.GamesUpdateEvent;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;

public class DiagramManager {
    public GamesUpdateEvent insert(Diagram tree, ArrayDeque<RawMove> moves, MetaData metaData) {
        if (moves.isEmpty()) {
            tree.getMetaData().add(metaData);
            return GamesUpdateEvent.of(metaData, tree);
        }

        if (tree.isLazy()) {
            if (tree.getLazyMovesList().isEmpty()) {
                tree.setLazyMoves(moves);
                tree.getMetaData().add(metaData);
                return GamesUpdateEvent.of(metaData, tree).join(updateMetadata(tree));
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
            return event.join(updateMetadata(diagram).join(insert(diagram, moves, metaData)));
        }

        Optional<Diagram> optionalDiagram = getByRawMove(tree, move);
        if (optionalDiagram.isPresent()) {
            return insert(optionalDiagram.get(), moves, metaData);
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

        return event.join(GamesUpdateEvent.of(metaData, diagram).join(updateMetadata(diagram)));
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

    private Optional<Diagram> getByRawMove(Diagram diagram, RawMove move) {
        if (diagram.isLazy()) {
            return Optional.empty();
        }
        for (Diagram nextDiagram : diagram.getNextDiagrams()) {
            if (nextDiagram.getCreatingMove().isPresent()) {
                if (nextDiagram.getCreatingMove().get().equals(move)) {
                    return Optional.of(nextDiagram);
                }
            }
        }
        return Optional.empty();
    }

    public GamesUpdateEvent updateMetadata(Diagram node) {
        Optional<Diagram> optionalParent = node.getParent();
        if (optionalParent.isEmpty()) {
            return GamesUpdateEvent.empty();
        }
        Diagram parent = optionalParent.get();

        return switch (parent.getNextDiagrams().size()) {
            case 1 -> moveMetaData(parent, node);
            case 2 -> moveMetaData(parent, getBrother(node));
            default -> GamesUpdateEvent.empty();
        };
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

    private Diagram getBrother(Diagram diagram) {
        Diagram parent = diagram.getParent().orElseThrow();
        Diagram diagram1 = parent.getNextDiagrams().get(0);
        Diagram diagram2 = parent.getNextDiagrams().get(1);
        if (diagram == diagram1) {
            return diagram2;
        } else if (diagram == diagram2) {
            return diagram1;
        }
        Log.log().fail("Too many diagrams to choose from");
        return diagram1;
    }
}
