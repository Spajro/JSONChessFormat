package dts;

import java.io.Serializable;

public class Bufor implements Serializable {
    private final int Size;
    private final int[][] T;
    Bufor(){
        Size=8;
        T= new int[8][8];
        for(int[] i : T){
            for(int j : i){
                j=0;
            }
        }
    }
    Bufor(int n){
        Size=n;
        T=new int[n][n];
        for(int[] i : T){
            for(int j : i){
                j=0;
            }
        }
    }
    Bufor(Bufor B){
        int n=B.GetSize();
        T=new int[n][n] ;
        Size=n;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                T[i][j]=B.get(i,j);
            }
        }
    }
    public void write(int val, int x,int y){
        T[x][y]=val;
    }
    public int get(int x,int y){
        return T[x][y];
    }
    public int GetSize(){
        return Size;
    }

}
