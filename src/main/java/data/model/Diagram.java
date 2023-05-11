package data.model;

import chess.moves.ExecutableMove;
import chess.results.MoveResult;
import chess.results.PromotionResult;
import chess.results.ValidMoveResult;
import chess.utility.LongAlgebraicFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.RawMove;
import data.model.games.GamesUpdateEvent;
import log.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Diagram {
    private final int moveId;
    private final String moveName;
    private Diagram parent;
    private final LinkedList<Diagram> nextDiagrams = new LinkedList<>();
    private final Annotations annotations = new Annotations();
    private final ExecutableMove creatingMove;
    private final LinkedList<MetaData> metaData = new LinkedList<>();

    public Diagram() {
        moveId = 0;
        moveName = "Root";
        parent = null;
        creatingMove = null;
    }

    public Diagram(ExecutableMove creatingMove, Diagram parent, int moveId) {
        this.creatingMove = creatingMove;
        this.parent = parent;
        this.moveId = moveId;
        if (parent != null) {
            moveName = LongAlgebraicFactory.getInstance().moveToLongAlgebraic(parent.getBoard(), creatingMove);
        } else {
            moveName = "Root";
        }
    }

    public Diagram makeMove(RawMove move, PromotionTypeProvider typeProvider) {
        Log.log().info("Trying to make:" + move);

        MoveResult moveResult = getBoard().makeMove(move);
        if (moveResult.isValid()) {
            ValidMoveResult validMoveResult;
            if (moveResult instanceof PromotionResult promotionResult) {
                validMoveResult = promotionResult.type(typeProvider.getPromotionType());
            } else {
                validMoveResult = (ValidMoveResult) moveResult;
            }

            Diagram nextDiagram = new Diagram(validMoveResult.getMove(), this, moveId + 1);
            for (Diagram diagram : nextDiagrams) {
                if (diagram.getBoard().equals(nextDiagram.getBoard()) && diagram.moveId == nextDiagram.moveId) {
                    return diagram;
                }
            }

            nextDiagrams.add(nextDiagram);
            return nextDiagram;
        } else {
            Log.log().info("Illegal Move");
            return this;
        }
    }

    public Diagram makeMove(ExecutableMove move) {
        Diagram nextDiagram = new Diagram(move, this, moveId + 1);
        for (Diagram diagram : nextDiagrams) {
            if (diagram.getBoard().equals(nextDiagram.getBoard()) && diagram.moveId == nextDiagram.moveId) {
                return diagram;
            }
        }

        nextDiagrams.add(nextDiagram);
        return nextDiagram;
    }

    public Diagram getDiagramOfId(int id) {
        if (id == moveId) {
            return this;
        }
        if (id > moveId || id < 0) {
            throw new IllegalArgumentException("illegal move id:" + id);
        } else {
            return parent.getDiagramOfId(id);
        }
    }

    public List<Diagram> getPathFromRoot() {
        if (parent == null) {
            return new LinkedList<>(List.of(this));
        } else {
            List<Diagram> result = parent.getPathFromRoot();
            result.add(this);
            return result;
        }
    }

    public GamesUpdateEvent insert(Diagram node) {
        if (this.partiallyEquals(node)) {
            return copyData(node).join(
                    node.getNextDiagrams().stream()
                            .map(
                                    diagram -> {
                                        List<Diagram> matching = nextDiagrams.stream().filter(diagram::partiallyEquals).toList();
                                        switch (matching.size()) {
                                            case 0 -> {
                                                diagram.setParent(this);
                                                nextDiagrams.add(diagram);
                                                return GamesUpdateEvent.empty();
                                            }
                                            case 1 -> {
                                                return matching.get(0).insert(diagram);
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

    private GamesUpdateEvent copyData(Diagram node) {
        annotations.addAll(node.getAnnotations());
        metaData.addAll(node.getMetaData());
        return GamesUpdateEvent.of(node.getMetaData(), this);
    }

    public GamesUpdateEvent updateMetadata() {
        if (metaData.isEmpty()) {
            Log.log().warn("Cant update metadata for diagram without it");
            return GamesUpdateEvent.empty();
        }

        if (parent == null) {
            return GamesUpdateEvent.empty();
        }

        if (parent.getMetaData().isEmpty()) {
            GamesUpdateEvent event = GamesUpdateEvent.of(metaData, parent);
            parent.getMetaData().addAll(metaData);
            metaData.clear();
            return event.join(parent.updateMetadata());
        } else {
            if (parent.getNextDiagrams().size() != 2) {
                Log.log().fail("Algorithm Axioms broken");
                return GamesUpdateEvent.empty();
            }

            Diagram brother = getOtherDiagramFromParent();
            brother.getMetaData().addAll(parent.getMetaData());
            parent.getMetaData().clear();
            return GamesUpdateEvent.of(brother.getMetaData(), brother);
        }
    }

    private Diagram getOtherDiagramFromParent() {
        Diagram diagram1 = parent.getNextDiagram(0);
        Diagram diagram2 = parent.getNextDiagram(1);
        if (this == diagram1) {
            return diagram2;
        } else if (this == diagram2) {
            return diagram1;
        }
        Log.log().fail("Too many diagrams to choose from");
        return diagram1;
    }

    public Diagram getRoot() {
        return getDiagramOfId(0);
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public Diagram getParent() {
        return parent;
    }

    public String getMoveName() {
        return moveName;
    }

    public LinkedList<Diagram> getNextDiagrams() {
        return nextDiagrams;
    }

    public ChessBoard getBoard() {
        if (parent == null) {
            return new ChessBoard();
        }
        List<ExecutableMove> moves = getPathFromRoot().stream()
                .skip(1)
                .map(diagram -> diagram.getCreatingMove().orElseThrow())
                .toList();

        ChessBoard result = getRoot().getBoard();
        for (ExecutableMove move : moves) {
            result = result.makeMove(move);
        }
        return result;
    }

    public Diagram getNextDiagram(int index) {
        if (index >= 0 && index < nextDiagrams.size()) {
            return nextDiagrams.get(index);
        }
        return null;
    }

    public int getNextDiagramsCount() {
        return nextDiagrams.size();
    }

    public int getIndexInNextDiagrams(Diagram d) {
        return nextDiagrams.indexOf(d);
    }

    @Override
    public String toString() {
        return moveName;
    }

    public boolean partiallyEquals(Diagram diagram) {
        return this.moveId == diagram.moveId &&
                this.moveName.equals(diagram.moveName) &&
                this.getBoard().equals(diagram.getBoard());
    }

    public void addMetadata(MetaData metaData) {
        this.metaData.add(metaData);
    }

    public LinkedList<MetaData> getMetaData() {
        return metaData;
    }

    public Optional<ExecutableMove> getCreatingMove() {
        return Optional.ofNullable(creatingMove);
    }

    private void setParent(Diagram parent) {
        this.parent = parent;
    }
}
