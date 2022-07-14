package dts;

import java.io.Serializable;

public class Move implements Serializable {
    //class representing chess move
    private String name;
    private int ox,oy,nx,ny;
    private int castle; //0 brak 1 bk 2 bd 3 ck 4 cd
    public static final int NO_CASTLE=0;
    public static final int WHITE_SHORT_CASTLE=1;
    public static final int WHITE_LONG_CASTLE=2;
    public static final int BLACK_SHORT_CASTLE=3;
    public static final int BLACK_LONG_CASTLE=4;
    public Move(){
        ox=0;
        oy=0;
        nx=0;
        ny=0;
        castle=0;
    }
    public Move(int aox,int aoy,int anx,int any){
        ox=aox;
        oy=aoy;
        nx=anx;
        ny=any;
        castle=0;
    }
    public Move(int castle){
        this.castle=castle;
    }
    public void update(int aox, int aoy, int anx, int any) {
        ox = aox;
        oy = aoy;
        nx = anx;
        ny = any;
    }
    public void setCastle(int castle){
        this.castle=castle;
    }
    public void makeMove(Board T){
        if(castle==0) {
            T.write(T.read(ox, oy), nx, ny);
            T.write(0, ox, oy);
        }
        else{
            //TODO
            //Roszady
        }
    }
    public boolean isCorrect(){
        return ox != -1 && oy != -1 && nx != -1 & ny != -1;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
