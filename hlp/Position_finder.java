package hlp;

import dts.Bufor;

public class Position_finder {
    public Position Result;
    Checker Check;

    public Position_finder() {
        Result= new Position(-1,-1);
        Check=new Checker();
    }

    public void Clean() {
        Result= new Position(-1,-1);
    }

    public void Choose_fig(char C, boolean Col, Bufor T, Position Start, Position Help) {
        switch (C) {
            case ' ' -> Pawn(Col, T, Start);
            case 'X' -> Pawn_Capture(Col, T, Start, Help);
            case 'W' -> Rook(Col, T, Start, Help);
            case 'S' -> Knight(Col, T, Start, Help);
            case 'G' -> Bishop(Col, T,Start, Help);
            case 'H' -> Queen(Col, T, Start, Help);
            case 'K' -> King(Col, T, ,Start, , Help);
            default -> System.out.print("Choose_fig fault");
        }
    }

    void Pawn_Capture(boolean Col, Bufor T, Position Start,Position Help) {
        //Manualnie
        if (Col) {
            Start.Add(0,1);
            if (Help.x == -1) {
                if (Start.x == 0) {
                    Result= new Position(Start);
                    Result.Add(1,0);
                }
                else if (Start.x == 7) {
                    Result= new Position(Start);
                    Result.Add(-1,0);
                }
                else {
                    if (T.get(Start.x + 1, Start.y) == 11) {
                        Result= new Position(Start);
                        Result.Add(1,0);
                    } else if (T.get(Start.x - 1, Start.y) == 11) {
                        Result= new Position(Start);
                        Result.Add(-1,0);
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            }
            else {
                Result= new Position(Help.x,Start.y);
            }
        } else {
            Start.Add(0,-1);
            if (Help.x == -1) {
                if (Start.x == 0) {
                    Result= new Position(Start);
                    Result.Add(1,0);
                }
                else if (Start.x == 7) {
                    Result= new Position(Start);
                    Result.Add(-1,0);
                }
                else {
                    if (T.get(Start.x + 1, Start.y) == 21) {
                        Result= new Position(Start);
                        Result.Add(1,0);
                    } else if (T.get(Start.x - 1, Start.y) == 21) {
                        Result= new Position(Start);
                        Result.Add(-1,0);
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            }
            else {
                Result= new Position(Help.x,Start.y);
            }
        }
    }

    void Pawn(boolean Col, Bufor T, Position Start,Position Help) {
        if (Col) {
            //biale

            while (sy <= 7) {
                if (T.get(sx, sy) == 11) {
                    x = sx;
                    y = sy;
                    break;
                }
                sy--;
            }
        }
        else {
            //czarne
            while (sy >= 0) {
                if (T.get(sx, sy) == 21) {
                    x = sx;
                    y = sy;
                    break;
                }
                sy++;
            }
        }
    }

    void Rook(boolean Col, Bufor T, Position Start,Position Help) {
        boolean found=false;
        int t1x = sx;
        while (t1x != 0 && !found) {
            if (Col) {
                if (T.get(t1x, sy) == 12) {
                    x=t1x;
                    y=sy;
                    found=true;
                }
            }
            else{
                if (T.get(t1x, sy) == 22) {
                    x=t1x;
                    y=sy;
                    found=true;
                }
            }
            t1x--;
        }
        int t2x = sx;
        while (t2x != 7 && !found) {
            if (Col) {
                if (T.get(t2x, sy) == 12) {
                    x=t2x;
                    y=sy;
                    found=true;
                }
            }
            else{
                if (T.get(t2x, sy) == 22) {
                    x=t2x;
                    y=sy;
                    found=true;
                }
            }
            t2x++;
        }
        int t1y = sy;
        while (t1y != 0 && !found) {
            if (Col) {
                if (T.get(sx, t1y) == 12) {
                    x=sx;
                    y=t1y;
                    found=true;
                }
            }
            else{
                if (T.get(sx, t1y) == 22) {
                    x=sx;
                    y=t1y;
                    found=true;
                }
            }
            t1y--;
        }
        int t2y=sy;
        while (t2y != 7 && !found) {
            if (Col) {
                if (T.get(sx, t2y) == 12) {
                    x=sx;
                    y=t2y;
                    found=true;
                }
            }
            else{
                if (T.get(sx, t2y) == 22) {
                    x=sx;
                    y=t2y;
                    found=true;
                }
            }
            t2y--;
        }
    }

    void Knight(boolean Col, Bufor T, Position Start,Position Help) {
        //TODO
    }

    void Bishop(boolean Col, Bufor T, Position Start,Position Help) {
        //TODO
        boolean found=false;
        if(hx==-1) {
            int t1x = sx, t2x = sx,t3x=sx,t4x=sx, t1y = sy, t2y = sy,t3y=sy,t4y=sy;

        }
        else{
          x=hx;
          if(Col){
              if(T.get(x,sy+sx-x)==14){
                  y=sy+sx-x;
              }
              else if(T.get(x,sy-sx+x)==14){
                  y=sy-sx+x;
              }
              else{
                  System.out.print("PF->bishop fault");
              }
          }
          else{
              if(T.get(x,sy+sx-x)==24){
                  y=sy+sx-x;
              }
              else if(T.get(x,sy-sx+x)==24){
                  y=sy-sx+x;
              }
              else{
                  System.out.print("PF->bishop fault");
              }
          }
        }
    }

    void Queen(boolean Col, Bufor T, Position Start,Position Help) {
        //TODO
    }

    void King(boolean Col, Bufor T,Position Start,Position Help) {
        if(Col){
            if(In_range(sx+1,sy+1)){
                if(T.get(sx+1,sy+1)==16){
                    x=sx+1;
                    y=sy+1;
                }
            }
            if(In_range(sx+1,sy)){
                if(T.get(sx+1,sy)==16){
                    x=sx+1;
                    y=sy;
                }
            }
            if(In_range(sx+1,sy-1)){
                if(T.get(sx+1,sy-1)==16){
                    x=sx+1;
                    y=sy-1;
                }
            }
            if(In_range(sx,sy+1)){
                if(T.get(sx,sy+1)==16){
                    x=sx;
                    y=sy+1;
                }
            }
            if(In_range(sx,sy-1)){
                if(T.get(sx,sy-1)==16){
                    x=sx;
                    y=sy-1;
                }
            }
            if(In_range(sx-1,sy+1)){
                if(T.get(sx-1,sy+1)==16){
                    x=sx-1;
                    y=sy+1;
                }
            }
            if(In_range(sx-1,sy)){
                if(T.get(sx-1,sy)==16){
                    x=sx-1;
                    y=sy;
                }
            }
            if(In_range(sx-1,sy-1)){
                if(T.get(sx-1,sy-1)==16){
                    x=sx-1;
                    y=sy-1;
                }
            }
        }
        else{
            if(In_range(sx+1,sy+1)){
                if(T.get(sx+1,sy+1)==26){
                    x=sx+1;
                    y=sy+1;
                }
            }
            if(In_range(sx+1,sy)){
                if(T.get(sx+1,sy)==26){
                    x=sx+1;
                    y=sy;
                }
            }
            if(In_range(sx+1,sy-1)){
                if(T.get(sx+1,sy-1)==26){
                    x=sx+1;
                    y=sy-1;
                }
            }
            if(In_range(sx,sy+1)){
                if(T.get(sx,sy+1)==26){
                    x=sx;
                    y=sy+1;
                }
            }
            if(In_range(sx,sy-1)){
                if(T.get(sx,sy-1)==26){
                    x=sx;
                    y=sy-1;
                }
            }
            if(In_range(sx-1,sy+1)){
                if(T.get(sx-1,sy+1)==26){
                    x=sx-1;
                    y=sy+1;
                }
            }
            if(In_range(sx-1,sy)){
                if(T.get(sx-1,sy)==26){
                    x=sx-1;
                    y=sy;
                }
            }
            if(In_range(sx-1,sy-1)){
                if(T.get(sx-1,sy-1)==26){
                    x=sx-1;
                    y=sy-1;
                }
            }
        }
    }
    boolean In_range(int x,int y){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
}
