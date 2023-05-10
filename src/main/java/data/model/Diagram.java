package data.model;

import chess.moves.ExecutableMove;
import chess.results.MoveResult;
import chess.results.PromotionResult;
import chess.results.ValidMoveResult;
import chess.utility.LongAlgebraicFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.RawMove;
import log.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Diagram {
    private final int moveId;
    private final String moveName;
    private final Diagram parent;
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

    public void insert(Diagram node) {
        if (this.partiallyEquals(node)) {
            copyData(node);
            node.getNextDiagrams().forEach(
                    diagram1 -> {
                        List<Diagram> matching = nextDiagrams.stream().filter(diagram1::partiallyEquals).toList();
                        switch (matching.size()) {
                            case 0 -> nextDiagrams.add(diagram1);
                            case 1 -> matching.get(0).insert(diagram1);
                            default -> Log.log().fail("Too many matching nodes to insert");
                        }
                    }
            );
        } else {
            Log.log().fail("Impossible to insert");
        }
    }

    private void copyData(Diagram node) {
        annotations.addAll(node.getAnnotations());
        metaData.addAll(node.getMetaData());
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
}
