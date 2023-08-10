package data.model;

import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;
import data.model.games.GamesUpdateEvent;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;

public class DiagramManager {
    public GamesUpdateEvent insert(Diagram tree, ArrayDeque<ExecutableMove> moves, MetaData metaData) {
        if (moves.isEmpty()) {
            tree.getMetaData().add(metaData);
            return GamesUpdateEvent.of(metaData, tree);
        }

        if (tree.isLazy() && tree.getLazyMoves().size() == 0) {
            tree.setLazyMoves(new ArrayDeque<>(
                    moves.stream()
                            .map(ExecutableMove::getRepresentation)
                            .toList()
            ));
            tree.getMetaData().add(metaData);
            return GamesUpdateEvent.of(metaData, tree).join(updateMetadata(tree));
        }

        ExecutableMove move = moves.poll();
        Optional<Diagram> optionalDiagram = getByRawMove(tree, move.getRepresentation());
        if (optionalDiagram.isPresent()) {
            return insert(optionalDiagram.get(), moves, metaData);
        }

        Diagram diagram = new Diagram(
                move,
                tree.getBoard(),
                tree,
                new ArrayDeque<>(moves.stream()
                        .map(ExecutableMove::getRepresentation)
                        .toList())
        );

        tree.getNextDiagrams().add(diagram);
        diagram.getMetaData().add(metaData);

        return GamesUpdateEvent.of(metaData, diagram).join(updateMetadata(diagram));
    }

    private Optional<Diagram> getByRawMove(Diagram diagram, RawMove move) {
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
            case 2 -> updateBrotherMetadata(node, parent);
            default -> GamesUpdateEvent.empty();
        };
    }

    private GamesUpdateEvent moveMetaData(Diagram from, Diagram to) {
        List<MetaData> gameData = from.getMetaData()
                .stream()
                .filter(metaData -> !(metaData instanceof GameData) || ((GameData) metaData).length() != from.depth())
                .toList();
        GamesUpdateEvent event = GamesUpdateEvent.of(gameData, to);
        to.getMetaData().addAll(gameData);
        from.getMetaData().removeAll(gameData);
        return event;
    }

    private GamesUpdateEvent updateBrotherMetadata(Diagram diagram, Diagram parent) {
        Diagram brother = getBrother(diagram);
        moveMetaData(parent, brother);
        return GamesUpdateEvent.of(brother.getMetaData(), brother);
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
