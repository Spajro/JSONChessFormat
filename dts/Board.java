package dts;

import java.awt.*;
import java.io.Serializable;

public class Board implements Serializable {
    //Class representing chess board
    //statics int%3==1 is white, 2 is black
    private static final int SIZE=8;
    private static final int EMPTY=0;
    private static final int WPAWN=1;
    private static final int WKNIGHT=4;
    private static final int WBISHOP=7;
    private static final int WROOK=10;
    private static final int WQUEEN=13;
    private static final int WKING=16;
    private static final int BPAWN=2;
    private static final int BKNIGHT=5;
    private static final int BBISHOP=8;
    private static final int BROOK=11;
    private static final int BQUEEN=14;
    private static final int BKING=17;
    //colors
    private static final int WHITE=101;
    private static final int BLACK=102;
    private final int[][] T;
    private Board(){
        T= new int[SIZE][SIZE];
        for(int[] i : T){
            for(int j : i){
                j=EMPTY;
            }
        }
    }

    private Board(Board B){
        T=new int[SIZE][SIZE];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                write(B.get(i,j),i,j);
            }
        }
    }
    public void write(int val, int x,int y){
        T[x+1][y+1]=val;
    }
    public int get(int x,int y){
        return T[x+1][y+1];
    }

    public static Board getBlank(){
        return new Board();
    }
    public static Board getStart(){
        Board b=new Board();
        for(int i=0;i<8;i++){
            b.write(WPAWN,i,2);
            b.write(BPAWN,i,7);
        }
        b.write(WROOK,1,1);
        b.write(WROOK,8,1);
        b.write(WKNIGHT,2,1);
        b.write(WKNIGHT,7,1);
        b.write(WBISHOP,3,1);
        b.write(WBISHOP,6,1);
        b.write(WQUEEN,4,1);
        b.write(WKING,5,1);
        b.write(BROOK,1,8);
        b.write(BROOK,8,8);
        b.write(BKNIGHT,2,8);
        b.write(BKNIGHT,7,8);
        b.write(BBISHOP,3,8);
        b.write(BBISHOP,6,8);
        b.write(BQUEEN,4,8);
        b.write(BKING,5,8);
        return b;
    }
    public static Board getCopy(Board b){
        return new Board(b);
    }
    public static int swapColor(int color){
        if(color==WHITE){
            return BLACK;
        }
        if(color==BLACK){
            return WHITE;
        }
        throw new IllegalArgumentException("illegal color code");
    }
    public static boolean isWhite(int color){
        if(color==WHITE){
            return true;
        }
        if(color==BLACK){
            return false;
        }
        throw new IllegalArgumentException("illegal color code");
    }public static boolean isBlack(int color){
        if(color==WHITE){
            return true;
        }
        if(color==BLACK){
            return false;
        }
        throw new IllegalArgumentException("illegal color code");
    }
    public Position checkLine(Position start, Position step, int figId){
        Position temp=new Position(start);
        while(temp.IsOnBoard()){
            if(checkPiece(figId,temp)){
                return temp;
            }
            else{
                if(!checkPiece(0,temp)){
                    //jesli na linii jest inna figura
                    break;
                }
                temp.Add(step);
            }
        }
        return new Position(-1,-1);
    }

    boolean checkPiece(int FigId,int x,int y){
        return get(x,y) == FigId;
    }
    boolean checkPiece(int FigId, Position temp){
        return checkPiece(FigId,temp.x,temp.y);
    }
}
