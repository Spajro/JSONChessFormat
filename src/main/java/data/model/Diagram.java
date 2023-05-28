package data.model;

import chess.moves.valid.executable.ExecutableMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.utility.LongAlgebraicFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Diagram {
    private Diagram parent;
    private final LinkedList<Diagram> nextDiagrams = new LinkedList<>();
    private final Annotations annotations = new Annotations();
    private final ExecutableMove creatingMove;
    private final LinkedList<MetaData> metaData = new LinkedList<>();

    public Diagram() {
        parent = null;
        creatingMove = null;
    }

    public Diagram(ExecutableMove creatingMove, Diagram parent) {
        this.creatingMove = creatingMove;
        this.parent = parent;
    }

    public Diagram makeMove(RawMove move, PromotionTypeProvider typeProvider) {
        Log.log().info("Trying to make:" + move);

        MoveResult moveResult = getBoard().makeMove(move);
        Optional<ValidMoveResult> validMoveResult = moveResult.validate(typeProvider);

        if (validMoveResult.isPresent()) {
            Diagram nextDiagram = new Diagram(validMoveResult.get().getExecutableMove(), this);
            for (Diagram diagram : nextDiagrams) {
                if (diagram.creatingMove != null && nextDiagram.creatingMove != null) {
                    if (diagram.creatingMove.equals(nextDiagram.creatingMove)) {
                        return diagram;
                    }
                }
            }

            nextDiagrams.add(nextDiagram);
            return nextDiagram;
        } else {
            Log.log().info("Illegal Move");
            return this;
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

    public Diagram getRoot() {
        return getParent()
                .map(Diagram::getRoot)
                .orElse(this);
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public Optional<Diagram> getParent() {
        return Optional.ofNullable(parent);
    }

    public String getMoveName() {
        if (parent != null) {
            return LongAlgebraicFactory.getInstance().moveToLongAlgebraic(parent.getBoard(), creatingMove);
        } else {
            return "Root";
        }
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

    @Override
    public String toString() {
        return getMoveName() + " | " + gamesInTree();
    }

    public boolean partiallyEquals(Diagram diagram) {
        return getMoveName().equals(diagram.getMoveName()) && parentsEquals(diagram);
    }

    private boolean parentsEquals(Diagram diagram) {
        if (parent == null && diagram.parent == null) {
            return true;
        }
        if (parent != null && diagram.parent != null) {
            return parent.partiallyEquals(diagram.parent);
        }
        return false;
    }

    public void addMetadata(MetaData metaData) {
        this.metaData.add(metaData);
    }

    public LinkedList<MetaData> getMetaData() {
        return metaData;
    }

    public List<GameData> getGameData() {
        return metaData.stream()
                .filter(m -> m instanceof GameData)
                .map(m -> (GameData) m)
                .toList();
    }

    public Optional<ExecutableMove> getCreatingMove() {
        return Optional.ofNullable(creatingMove);
    }

    public void setParent(Diagram parent) {
        this.parent = parent;
    }

    private int gamesInTree() {
        //TODO for debug purposes only
        if (!metaData.isEmpty()) {
            return metaData.size();
        } else {
            return nextDiagrams.stream().mapToInt(Diagram::gamesInTree).sum();
        }
    }
}
