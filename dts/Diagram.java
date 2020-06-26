package dts;

import ant.Annotation;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    public LinkedList<Diagram> Story;
    public LinkedList<Diagram> Next_moves;
    Annotation Info;
    public Bufor T;
    public Diagram(){
        T=new Bufor(8);
        Story=new LinkedList<>();
        Next_moves = new LinkedList<>();
        Info = new Annotation();
    }
    public Diagram(Bufor NT){
        T=NT;
        Story=new LinkedList<>();
        Next_moves = new LinkedList<>();
        Info = new Annotation();
    }
    public Diagram(Bufor NT, LinkedList<Diagram> S){
        T=NT;
        Story=S;
        Next_moves = new LinkedList<>();
        Info = new Annotation();
    }
    public Diagram Make_move(Move M){
        //TODO
        //poprawka zeby nie się nie dublowalo
        LinkedList<Diagram>NS=Story;
        Bufor NT=T;
        M.Make_move(NT);
        NS.add(this);
        Diagram Next=new Diagram(NT,NS);
        Next_moves.add(Next);
    return Next;
    }
    public Diagram Original(){
        return Story.get(0);
    }
}
