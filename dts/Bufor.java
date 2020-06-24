package dts;

public class Bufor {
    private final int[][] T;
    Bufor(int n){
        T=new int[n][n];
        for(int[] i : T){
            for(int j : i){
                j=0;
            }
        }
    }
    public void write(int val, int x,int y){
        T[x][y]=val;
    }
    public int get(int x,int y){
        return T[x][y];
    }

}
