package hlp;

import dts.Bufor;

public class Position_finder {
    public int x, y;

    public Position_finder() {
        x = -1;
        y = -1;
    }

    public void Clean() {
        x = -1;
        y = -1;
    }

    public void Choose_fig(char C, boolean Col, Bufor T, int sx, int sy, int hx) {
        switch (C) {
            case ' ' -> Pawn(Col, T, sx, sy);
            case 'X' -> Pawn_Capture(Col, T, sx, sy, hx);
            case 'W' -> Rook(Col, T, sx, sy, hx);
            case 'S' -> Knight(Col, T, sx, sy, hx);
            case 'G' -> Bishop(Col, T, sx, sy, hx);
            case 'H' -> Queen(Col, T, sx, sy, hx);
            case 'K' -> King(Col, T, sx, sy);
            default -> System.out.print("Choose_fig fault");
        }
    }

    void Pawn_Capture(boolean Col, Bufor T, int sx, int sy, int hx) {
        //TODO
    }

    void Pawn(boolean Col, Bufor T, int sx, int sy) {
        if(Col){
            //biale
            while(sy>=7){
                if(T.get(sx,sy)==11){
                    x=sx;
                    y=sy;
                    break;
                }
                sy--;
            }
        }
        else{
            //czarne
            while(sy>=0){
                if(T.get(sx,sy)==21){
                    x=sx;
                    y=sy;
                    break;
                }
                sy++;
            }
        }
    }

    void Rook(boolean Col, Bufor T, int sx, int sy, int hx) {
        //TODO
    }

    void Knight(boolean Col, Bufor T, int sx, int sy, int hx) {
        //TODO
    }

    void Bishop(boolean Col, Bufor T, int sx, int sy, int hx) {
        //TODO
    }

    void Queen(boolean Col, Bufor T, int sx, int sy, int hx) {
        //TODO
    }

    void King(boolean Col, Bufor T, int sx, int sy) {
        //TODO
    }
}
