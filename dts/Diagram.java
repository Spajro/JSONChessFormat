package dts;

import java.io.Serializable;
import java.util.LinkedList;

public class Diagram implements Serializable {
    LinkedList<Move> Story;
    Annotation Info;
    int[][] T;
    public Diagram(){
        T=new int[8][8];
    }
    public Diagram(int[][] NT){
        T=NT;
    }
    Diagram Make_move(Move M){

        return null;
    }
}
