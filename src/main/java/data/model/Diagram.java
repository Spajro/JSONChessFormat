package data.model;

import chess.results.MoveResult;
import chess.results.PromotionResult;
import chess.results.ValidMoveResult;
import chess.utility.AlgebraicFactory;
import data.json.Jsonable;
import data.json.ListJsonFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.moves.RawMove;
import log.Log;

import java.util.LinkedList;
import java.util.List;


public class Diagram implements Jsonable {
    private final int moveId;
    private String moveName = "diag";
    private final Diagram parent;
    private final LinkedList<Diagram> nextDiagrams = new LinkedList<>();
    private final Annotations annotations = new Annotations();
    private final ChessBoard board;
    private final AlgebraicFactory algebraicFactory = AlgebraicFactory.getInstance();

    public Diagram() {
        moveId = 0;
        moveName = "Start";
        board = new ChessBoard();
        parent = null;
    }

    public Diagram(ChessBoard nextBoard, Diagram last, int id) {
        moveId = id;
        board = nextBoard;
        parent = last;
    }

    public Diagram makeMove(RawMove move, PromotionTypeProvider typeProvider) {
        Log.log().info("Trying to make:" + move);

        MoveResult moveResult = board.makeMove(move);
        if (moveResult.isValid()) {
            ValidMoveResult validMoveResult;
            if (moveResult instanceof PromotionResult promotionResult) {
                validMoveResult = promotionResult.type(typeProvider.getPromotionType());
            } else {
                validMoveResult = (ValidMoveResult) moveResult;
            }

            ChessBoard tempBoard = validMoveResult.getResult();
            Diagram nextDiagram = new Diagram(tempBoard, this, moveId + 1);
            nextDiagram.moveName = algebraicFactory.moveToLongAlgebraic(this.getBoard(), validMoveResult.getMove());
            for (Diagram diagram : nextDiagrams) {
                if (diagram.board.equals(nextDiagram.board) && diagram.moveId == nextDiagram.moveId) {
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

    public Diagram getDiagramOfId(int id) {
        if (id == moveId) {
            return this;
        }
        if (id > moveId || id < 0) {
            throw new IllegalArgumentException("illegal move id:" + id);
        } else {
            assert parent != null;
            return parent.getDiagramOfId(id);
        }
    }

    public List<Diagram> getPathFromRoot() {
        if (moveId == 0) {
            return new LinkedList<>(List.of(this));
        } else {
            assert parent != null;
            List<Diagram> result = parent.getPathFromRoot();
            result.add(this);
            return result;
        }
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
        return board;
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

    public String toJson() {
        return "{\"moveName\":\"" +
                moveName +
                "\",\"moves\":" +
                ListJsonFactory.listToJson(nextDiagrams) +
                ",\"annotations\":" +
                annotations.toJson() +
                "}";

    }
}
