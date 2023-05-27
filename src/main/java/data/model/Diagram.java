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
        Optional<ValidMoveResult> validMoveResult = moveResult.validate(typeProvider);

        if (validMoveResult.isPresent()) {
            Diagram nextDiagram = new Diagram(validMoveResult.get().getExecutableMove(), this, moveId + 1);
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

    @Override
    public String toString() {
        return moveName + " | " + gamesInTree();
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

    public int getMoveId() {
        return moveId;
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
