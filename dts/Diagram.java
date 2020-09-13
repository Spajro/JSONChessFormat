package dts;

import ant.Annotation;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    public int MoveId;
    public Diagram LastMove;
    public LinkedList<Diagram> Next_moves;
    Annotation Info;
    public Bufor T;
    public boolean Color;
    public Diagram(){
        MoveId=0;
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
            for (Diagram D : Next_moves) {
                if (D.T.equals(Next.T) && D.MoveId==Next.MoveId) {
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
}
