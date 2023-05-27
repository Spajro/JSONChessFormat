package data.model;

import data.model.games.GamesUpdateEvent;
import data.model.metadata.MetaData;
import log.Log;

import java.util.List;
import java.util.Optional;

public class DiagramManager {
    public GamesUpdateEvent insert(Diagram tree, Diagram node) {
        if (tree.partiallyEquals(node)) {
            return copyData(tree, node).join(
                    node.getNextDiagrams().stream()
                            .map(
                                    diagram -> {
                                        List<Diagram> matching = tree.getNextDiagrams().stream().filter(diagram::partiallyEquals).toList();
                                        switch (matching.size()) {
                                            case 0 -> {
                                                diagram.setParent(tree);
                                                tree.getNextDiagrams().add(diagram);
                                                return GamesUpdateEvent.empty();
                                            }
                                            case 1 -> {
                                                return insert(matching.get(0), diagram);
                                            }
                                            default -> {
                                                Log.log().fail("Too many matching nodes to insert");
                                                return GamesUpdateEvent.empty();
                                            }
                                        }
                                    })
                            .reduce(GamesUpdateEvent.empty(),
                                    GamesUpdateEvent::join)
            );
        } else {
            Log.log().fail("Impossible to insert");
            return GamesUpdateEvent.empty();
        }
    }

    private GamesUpdateEvent copyData(Diagram to, Diagram from) {
        to.getAnnotations().addAll(from.getAnnotations());
        to.getMetaData().addAll(from.getMetaData());
        return GamesUpdateEvent.of(from.getMetaData(), to);
    }

    public GamesUpdateEvent updateMetadata(Diagram node) {
        if (node.getMetaData().isEmpty()) {
            Log.log().warn("Cant update metadata for diagram without it");
            return GamesUpdateEvent.empty();
        }

        Optional<Diagram> optionalParent = node.getParent();
        if (optionalParent.isEmpty()) {
            return GamesUpdateEvent.empty();
        }
        Diagram parent = optionalParent.get();
        if (parent.getNextDiagramsCount() == 1) {
            GamesUpdateEvent event = GamesUpdateEvent.of(node.getMetaData(), parent);
            parent.getMetaData().addAll(node.getMetaData());
            node.getMetaData().clear();
            return event.join(updateMetadata(parent));
        } else if (parent.getNextDiagramsCount() == 2) {
            Diagram brother = getOtherDiagramFromParent(node);
            brother.getMetaData().addAll(getMetadataFromPathToRoot(parent));
            return GamesUpdateEvent.of(brother.getMetaData(), brother);
        } else {
            return GamesUpdateEvent.empty();
        }
    }

    private List<MetaData> getMetadataFromPathToRoot(Diagram node) {
        if (!node.getMetaData().isEmpty()) {
            List<MetaData> result = List.copyOf(node.getMetaData());
            node.getMetaData().clear();
            return result;
        } else {
            if (node.getParent().isEmpty()) {
                Log.log().fail("no metadata on path to root");
                return List.of();
            } else {
                return getMetadataFromPathToRoot(node.getParent().get());
            }
        }
    }

    private Diagram getOtherDiagramFromParent(Diagram diagram) {
        Diagram parent = diagram.getParent().orElseThrow();
        Diagram diagram1 = parent.getNextDiagram(0);
        Diagram diagram2 = parent.getNextDiagram(1);
        if (diagram == diagram1) {
            return diagram2;
        } else if (diagram == diagram2) {
            return diagram1;
        }
        Log.log().fail("Too many diagrams to choose from");
        return diagram1;
    }
}
