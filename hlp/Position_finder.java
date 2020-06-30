package hlp;

import dts.Bufor;

public class Position_finder {
    public int x,y;
    public Position_finder(){
        x=-1;
        y=-1;
    }
    public void Choose_fig(char C,Bufor T,int sx,int sy,int hx){
        switch (C){
            case ' ' ->Pawn(T,sx,sy);
            case 'X' ->Pawn_Capture(T,sx,sy,hx);
            case 'W' ->Rook(T,sx,sy,hx);
            case 'S' ->Knight(T,sx,sy,hx);
            case 'G' ->Bishop(T,sx,sy,hx);
            case 'H' ->Queen(T,sx,sy,hx);
            case 'K' ->King(T,sx,sy);
            default -> System.out.print("Choose_fig fault");
        }
    }
    void Pawn_Capture(Bufor T,int sx,int sy,int hx){
        //TODO
    }
    void Pawn(Bufor T,int sx,int sy){
        //TODO
    }
     void Rook(Bufor T,int sx,int sy,int hx){
        //TODO
    }
     void Knight(Bufor T,int sx,int sy,int hx){
        //TODO
    }
     void Bishop(Bufor T,int sx,int sy,int hx){
        //TODO
    }
    void Queen(Bufor T,int sx,int sy,int hx){
        //TODO
    }
     void King(Bufor T,int sx,int sy){
        //TODO
    }
}
