package src.data.dts;

import src.data.dts.color.Color;

import java.io.Serializable;

public class Move implements Serializable {
    //class representing chess move
    private String name;
    private Position oldPosition;
    private Position newPosition;
    private int castle; //0 brak 1 bk 2 bd 3 ck 4 cd
    public static final int NO_CASTLE=0;
    public static final int WHITE_SHORT_CASTLE=1;
    public static final int WHITE_LONG_CASTLE=2;
    public static final int BLACK_SHORT_CASTLE=3;
    public static final int BLACK_LONG_CASTLE=4;
    public Move(){
        oldPosition=new Position();
        newPosition=new Position();
        castle=-1;
    }
    public Move(Position oldPos, Position newPos){
        oldPosition=oldPos;
        newPosition=newPos;
        castle=0;
    }
    public Move(int castle){
        this.castle=castle;
    }
    public void setCastle(int castle){
        this.castle=castle;
    }
    public void makeMove(Board board){
        if(castle==0) {
            board.write(board.read(oldPosition),newPosition);
            board.write(0, oldPosition);
        }
        else{
            //TODO`
            //Roszady
        }
    }

    public boolean isLegal(Board board, Color color){
        return Board.getPieceColor(board.read(oldPosition)).equal(color) && isCorrect();
    }
    private boolean isCorrect(){
        return !oldPosition.isEmpty() && !newPosition.isEmpty();
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

    public Position getOldPosition() {
        return oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }
}
