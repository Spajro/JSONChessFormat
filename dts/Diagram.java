package dts;

import ant.Annotation;
import ant.Annotation_Editor;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    //class representing node of openings tree
    public int MoveId;
    public String MoveName;
    public Diagram LastMove;
    public LinkedList<Diagram> Next_moves;
    public Annotation Info;
    public Board T;
    public int Color;
    public Diagram(){
        MoveId=0;
        MoveName=null;
        T=Board.getBlank();
        LastMove=null;
        Next_moves = new LinkedList<>();
        Info = new Annotation();
        Color=-1;
    }
    public Diagram(Board NT, Diagram Last, int C, int Id){
        MoveId=Id;
        T= Board.getCopy(NT);
        LastMove=Last;
        Next_moves = new LinkedList<>();
        Info = new Annotation();
        Color=C;
    }
    public Diagram Make_move(Move M){
        if(M.IsCorrect()) {
            Board NT =Board.getCopy(T);
            M.Make_move(NT);
            Diagram Next = new Diagram(NT, this, Board.swapColor(Color),MoveId+1);
            Next.MoveName=M.GetName();
            for (Diagram D : Next_moves) {
                if (D.T.equals(Next.T) && D.MoveId==Next.MoveId) {
                    //TODO
                    return D;
                }
            }
            Next_moves.add(Next);
            return Next;
        }
        else{
            System.out.print("Move Corrupted");
            return this;
        }
    }
    public Diagram FindMove(int Id){
        if(MoveId==Id){
            return this;
        }
        else if(Id<MoveId){
            return  LastMove.FindMove(Id);
        }
        else{
            System.out.print("Findmove fail");
            return null;
        }
    }
    public Diagram Original(){
        return FindMove(0);
    }
    public String[] GetMoves(){
        int n=Next_moves.size();
        if(n==0){
            System.out.print("No moves");
            return null;
        }
        else{
            String[] S=new String[n];
            int i=0;
            for(Diagram D : Next_moves){
                S[i]=D.MoveName;
                i++;
            }
            return S;
        }
    }
    public String[] GetHistory(){
        if(MoveId!=0) {
            Diagram D = this;
            String[] S = new String[D.MoveId+1];
            while (D.MoveId >= 0) {
                S[D.MoveId] = D.MoveName;
                D = D.LastMove;
                if(D==null){
                    break;
                }
            }
            return S;
        }
        else return null;
    }
}
