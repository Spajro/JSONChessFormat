package data.model;

import chess.moves.valid.executable.ExecutableMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.formats.algebraic.LongAlgebraicFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.*;

public class Diagram {
    private final String moveName;
    private final Diagram parent;
    private ArrayList<Diagram> nextDiagrams;
    private ArrayDeque<RawMove> lazyMoves;
    private final Annotations annotations = new Annotations();
    private final RawMove creatingMove;
    private final ArrayList<MetaData> metaData = new ArrayList<>();

    public Diagram() {
        parent = null;
        creatingMove = null;
        moveName = "Root";
        nextDiagrams = new ArrayList<>();
    }

    public Diagram(ExecutableMove creatingMove, ChessBoard chessBoard, Diagram parent) {
        nextDiagrams = new ArrayList<>();
        this.parent = parent;

        if (creatingMove == null) {
            this.creatingMove = null;
        } else {
            this.creatingMove = creatingMove.getRepresentation();
        }

        if (parent != null) {
            moveName = LongAlgebraicFactory.getInstance().moveToLongAlgebraic(chessBoard, creatingMove);
        } else {
            moveName = "Root";
        }
    }

    public Diagram(ExecutableMove creatingMove, ChessBoard chessBoard, Diagram parent, ArrayDeque<RawMove> moves) {
        this.parent = parent;
        this.lazyMoves = moves;

        if (creatingMove == null) {
            this.creatingMove = null;
        } else {
            this.creatingMove = creatingMove.getRepresentation();
        }

        if (parent != null) {
            moveName = LongAlgebraicFactory.getInstance().moveToLongAlgebraic(chessBoard, creatingMove);
        } else {
            moveName = "Root";
        }
    }

    public Diagram makeMove(RawMove move, PromotionTypeProvider typeProvider) {
        Log.log().info("Trying to make:" + move);

        ChessBoard chessBoard = getBoard();
        MoveResult moveResult = chessBoard.makeMove(move);
        Optional<ValidMoveResult> validMoveResult = moveResult.validate(typeProvider);

        if (validMoveResult.isPresent()) {
            Diagram nextDiagram = new Diagram(validMoveResult.get().getExecutableMove(), chessBoard, this);
            for (Diagram diagram : getNextDiagrams()) {
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
        if (lazyMoves != null) {
            expand();
        }
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
            result = result.makeMove(move).validate(null).orElseThrow().getResult();
        }
        return result;
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

    private List<GameData> getNonEndingGameData() {
        return metaData.stream()
                .filter(metaData -> metaData instanceof GameData)
                .map(metaData -> (GameData) metaData)
                .filter(gameData -> gameData.length() != depth())
                .toList();
    }

    private int gamesInTree() {
        //TODO for debug purposes only
        if (!getNonEndingGameData().isEmpty() || lazyMoves != null || nextDiagrams == null) {
            return metaData.size();
        } else {
            return metaData.size() + nextDiagrams.stream().mapToInt(Diagram::gamesInTree).sum();
        }
    }

    public void expand() {
        RawMove move = lazyMoves.poll();
        if (move == null) {
            nextDiagrams = new ArrayList<>();
            lazyMoves = null;
            return;
        }
        ChessBoard chessBoard = getBoard();
        Optional<ValidMoveResult> validMoveResult = chessBoard.makeMove(move).validate(null);
        if (validMoveResult.isEmpty()) {
            throw new IllegalStateException();
        }

        Diagram lazy = new Diagram(
                validMoveResult.get().getExecutableMove(),
                chessBoard,
                this,
                lazyMoves
        );
        nextDiagrams = new ArrayList<>();
        lazyMoves = null;
        this.getNextDiagrams().add(lazy);
    }

    public boolean isLazy() {
        return nextDiagrams == null;
    }

    public List<RawMove> getLazyMoves() {
        if (lazyMoves == null) {
            return null;
        }
        return lazyMoves.stream().toList();
    }

    public void setLazyMoves(ArrayDeque<RawMove> lazyMoves) {
        this.lazyMoves = lazyMoves;
    }
}
