package dts;

import java.io.Serializable;

public class Move implements Serializable {
    //class representing chess move
    private String name;
    private int oldX, oldY,newX, newY;
    private int castle; //0 brak 1 bk 2 bd 3 ck 4 cd
    public static final int NO_CASTLE=0;
    public static final int WHITE_SHORT_CASTLE=1;
    public static final int WHITE_LONG_CASTLE=2;
    public static final int BLACK_SHORT_CASTLE=3;
    public static final int BLACK_LONG_CASTLE=4;
    public Move(){
        oldX =0;
        oldY =0;
        newX=0;
        newY =0;
        castle=0;
    }
    public Move(int aox,int aoy,int anx,int any){
        oldX =aox;
        oldY =aoy;
        newX=anx;
        newY =any;
        castle=0;
    }
    public Move(int castle){
        this.castle=castle;
    }
    public void update(int aox, int aoy, int anx, int any) {
        oldX = aox;
        oldY = aoy;
        newX = anx;
        newY = any;
    }
    public void setCastle(int castle){
        this.castle=castle;
    }
    public void makeMove(Board T){
        if(castle==0) {
            T.write(T.read(oldX, oldY), newX, newY);
            T.write(0, oldX, oldY);
        }
        else{
            //TODO
            //Roszady
        }
    }
    public boolean isCorrect(){
        return oldX != -1 && oldY != -1 && newX != -1 & newY != -1;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public int getCastle() {
        return castle;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }
}
