package dts;

import ant.Annotation;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    LinkedList<Diagram> Story;
    LinkedList<Diagram> Next_moves;
    Annotation Info;
    Bufor T;
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
    Diagram Make_move(Move M){
        LinkedList<Diagram>NS=Story;
        Bufor NT=T;
        M.Make_move(NT);
        NS.add(this);
        return new Diagram(NT,NS);
    }
    void Put(int val, int x,int y){
        T.write(val,x,y);
    }
}
