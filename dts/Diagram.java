package dts;

import ant.Annotation;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    public int MoveId;
    public String MoveName;
    public Diagram LastMove;
    public LinkedList<Diagram> Next_moves;
    Annotation Info;
    public Bufor T;
    public boolean Color;
    public Diagram(){
        MoveId=0;
        MoveName=null;
        T=new Bufor(8);
        LastMove=null;
        Next_moves = new LinkedList<>();
        Info = new Annotation();
        Color=true;
    }
    public Diagram(Bufor NT, Diagram Last, boolean C,int Id){
        MoveId=Id;
        T= new Bufor(NT);
        LastMove=Last;
        Next_moves = new LinkedList<>();
        Info = new Annotation();
        Color=C;
    }
    public Diagram Make_move(Move M){
        if(M.IsCorrect()) {
            Bufor NT = new Bufor(T);
            M.Make_move(NT);
            Diagram Next = new Diagram(NT, this, !Color,MoveId+1);
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
}
