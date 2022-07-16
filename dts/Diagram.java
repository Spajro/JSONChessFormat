package dts;

import ant.Annotation;
import dts.color.Color;
import hlp.Translator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Diagram implements Serializable {
    //TODO diagram naming
    //class representing node of openings tree
    private final int moveId;
    private String moveName;
    private final Diagram parent;
    private LinkedList<Diagram> nextDiagrams;
    private Annotation annotation;
    private Board board;
    private Color color;

    public Diagram() {
        moveId = 0;
        moveName = "Start";
        board = Board.getStart();
        parent = null;
        nextDiagrams = new LinkedList<>();
        annotation = new Annotation();
        color = Color.white;
    }

    public Diagram(Board nextBoard, Diagram last, Color color, int id) {
        moveId = id;
        moveName = "newDiag";
        board = Board.getCopy(nextBoard);
        parent = last;
        nextDiagrams = new LinkedList<>();
        annotation = new Annotation();
        this.color = color;
    }

    public Diagram makeMove(Move move) {
        if (move.isCorrect()) {
            Board nextBoard = Board.getCopy(board);
            move.setName(Translator.preMoveToAlgebraic(board,move));
            move.makeMove(nextBoard);
            Diagram nextDiagram = new Diagram(nextBoard, this, color.swap(), moveId + 1);
            nextDiagram.moveName=move.getName();
            for (Diagram D : nextDiagrams) {
                if (D.board.equals(nextDiagram.board) && D.moveId == nextDiagram.moveId) {
                    //TODO
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

    public Board getBoard() {
        return board;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Diagram getParent() {
        return parent;
    }

    public LinkedList<Diagram> getNextDiagrams() {
        return nextDiagrams;
    }

    public Color getColor() {
        return color;
    }

    public Diagram getNextDiagram(int index){
        if(index>=0 && index< nextDiagrams.size()){
            return nextDiagrams.get(index);
        }
        return null;
    }

    public int getNextDiagramsCount(){
        return nextDiagrams.size();
    }

    public int getIndexInNextDiagrams(Diagram d){
        return nextDiagrams.indexOf(d);
    }

    @Override
    public String toString() {
        return moveName;
    }

    public List<Diagram> getPathFromOriginal(){
        List<Diagram> list=new ArrayList<>(moveId+1);
        Diagram temp=this;
        while(temp!=null){
            list.add(0,temp);
            temp=temp.parent;
        }
        return list;
    }
}
