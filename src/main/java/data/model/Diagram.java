package data.model;

import chess.ValidMoveFactory;
import data.Jsonable;
import data.ListJsonFactory;
import data.annotations.Annotations;
import chess.board.ChessBoard;
import chess.exceptions.ChessAxiomViolation;
import chess.exceptions.IllegalMoveException;
import chess.moves.RawMove;
import chess.hlp.Translator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Diagram implements Jsonable {
    private final int moveId;
    private String moveName;
    private final Diagram parent;
    private final LinkedList<Diagram> nextDiagrams;
    private final Annotations annotations;
    private final ChessBoard board;

    public Diagram() {
        moveId = 0;
        moveName = "Start";
        board = new ChessBoard();
        parent = null;
        nextDiagrams = new LinkedList<>();
        annotations = new Annotations();
    }

    public Diagram(ChessBoard nextBoard, Diagram last, int id) {
        moveId = id;
        moveName = "newDiag";
        board = nextBoard;
        parent = last;
        nextDiagrams = new LinkedList<>();
        annotations = new Annotations();
    }

    public Diagram makeMove(RawMove move) {
        System.out.print(move);
        ChessBoard tempBoard;
        try {
            tempBoard = board.makeMove(move);
        } catch (IllegalMoveException e) {
            System.out.print("Illegal Move\n");
            return this;
        } catch (ChessAxiomViolation e) {
            throw new RuntimeException(e);
        }
        if (board != tempBoard) {
            Diagram nextDiagram = new Diagram(tempBoard, this, moveId + 1);
            try {
                nextDiagram.moveName = Translator.preMoveToAlgebraic(this.getBoard(), new ValidMoveFactory(board).createValidMove(move));
            } catch (IllegalMoveException | ChessAxiomViolation e) {
                throw new RuntimeException(e);
            }
            for (Diagram D : nextDiagrams) {
                if (D.board.equals(nextDiagram.board) && D.moveId == nextDiagram.moveId) {
                    return D;
                }
            }
            nextDiagrams.add(nextDiagram);
            return nextDiagram;
        } else {
            System.out.print("Move Corrupted");
            return this;
        }
    }

    public Diagram findMove(int Id) {
        if (moveId == Id) {
            return this;
        } else if (Id < moveId) {
            return parent.findMove(Id);
        } else {
            System.out.print("Findmove fail");
            return null;
        }
    }

    public Diagram getOriginal() {
        return findMove(0);
    }

    public String[] getMoves() {
        int n = nextDiagrams.size();
        if (n == 0) {
            System.out.print("No moves");
            return null;
        } else {
            String[] S = new String[n];
            int i = 0;
            for (Diagram D : nextDiagrams) {
                S[i] = D.moveName;
                i++;
            }
            return S;
        }
    }

    public String[] getHistory() {
        if (moveId != 0) {
            Diagram D = this;
            String[] S = new String[D.moveId + 1];
            while (D.moveId >= 0) {
                S[D.moveId] = D.moveName;
                D = D.parent;
                if (D == null) {
                    break;
                }
            }
            return S;
        } else return null;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public Diagram getParent() {
        return parent;
    }

    public LinkedList<Diagram> getNextDiagrams() {
        return nextDiagrams;
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

    public List<Diagram> getPathFromOriginal() {
        List<Diagram> list = new ArrayList<>(moveId + 1);
        Diagram temp = this;
        while (temp != null) {
            list.add(0, temp);
            temp = temp.parent;
        }
        return list;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public String toJson() {
        return "{moveName:" +
                moveName +
                ",moves:" +
                ListJsonFactory.listToJson(nextDiagrams) +
                ",annotations:" +
                annotations.toJson() +
                "}";

    }
}
