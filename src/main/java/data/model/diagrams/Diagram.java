package data.model.diagrams;

import chess.formats.algebraic.LongAlgebraicFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;

import java.util.*;

public class Diagram {
    private final String moveName;
    private final Diagram parent;
    private ArrayList<Diagram> nextDiagrams;
    private ArrayDeque<RawMove> lazyMoves;
    private final Annotations annotations = new Annotations();
    private final RawMove creatingMove;
    private final ArrayList<MetaData> metaData = new ArrayList<>();
    private boolean isBest = false;

    public Diagram() {
        parent = null;
        creatingMove = null;
        moveName = "Root";
        nextDiagrams = new ArrayList<>();
    }

    public Diagram(RawMove creatingMove, ChessBoard chessBoard, Diagram parent) {
        nextDiagrams = new ArrayList<>();
        this.parent = parent;
        this.creatingMove = creatingMove;

        if (parent != null) {
            moveName = LongAlgebraicFactory.getInstance().moveToLongAlgebraic(chessBoard, creatingMove);
        } else {
            moveName = "Root";
        }
    }

    public Diagram(RawMove creatingMove, ChessBoard chessBoard, Diagram parent, ArrayDeque<RawMove> moves) {
        this.parent = parent;
        this.lazyMoves = moves;
        this.creatingMove = creatingMove;
        this.moveName = LongAlgebraicFactory.getInstance().moveToLongAlgebraic(chessBoard, creatingMove);
    }

    public List<Diagram> getPathFromRoot() {
        if (parent == null) {
            return new ArrayList<>(List.of(this));
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

    public List<Diagram> getNextDiagrams() {
        return nextDiagrams;
    }

    public ChessBoard getBoard() {
        if (parent == null) {
            return new ChessBoard();
        }
        ChessBoard result = new ChessBoard();
        List<RawMove> moves = getPathFromRoot().stream()
                .skip(1)
                .map(diagram -> diagram.getCreatingMove().orElseThrow())
                .toList();

        for (RawMove move : moves) {
            result = result.makeMove(move).validate().orElseThrow().getResult();
        }
        return result;
    }

    @Override
    public String toString() {
        if (isBest) {
            return "!! " + moveName;
        }
        return moveName;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public List<GameData> getGameData() {
        return metaData.stream()
                .filter(m -> m instanceof GameData)
                .map(m -> (GameData) m)
                .toList();
    }

    public Optional<RawMove> getCreatingMove() {
        return Optional.ofNullable(creatingMove);
    }

    public int depth() {
        Diagram diagram = this;
        int result = 0;
        while (diagram.parent != null) {
            result++;
            diagram = diagram.parent;
        }
        return result;
    }

    public boolean isLazy() {
        return nextDiagrams == null;
    }

    public List<RawMove> getLazyMovesList() {
        if (lazyMoves == null) {
            return null;
        }
        return lazyMoves.stream().toList();
    }

    public ArrayDeque<RawMove> getLazyMovesDeque() {
        return lazyMoves;
    }

    public void setLazyMoves(ArrayDeque<RawMove> lazyMoves) {
        this.lazyMoves = lazyMoves;
    }

    public void expandNextDiagrams() {
        nextDiagrams = new ArrayList<>();
    }

    public void setAsBest() {
        if (parent == null) {
            return;
        }

        isBest = true;

        if (parent.isLazy()) {
            return;
        }

        parent.nextDiagrams.stream()
                .filter(d -> d != this)
                .forEach(Diagram::setBestAsFalse);
    }

    private void setBestAsFalse() {
        isBest = false;
    }

    public boolean isBest() {
        return isBest;
    }
}
