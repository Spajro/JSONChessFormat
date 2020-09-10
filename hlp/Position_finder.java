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
            case ' ' -> Pawn(Col, T, Start,Help);
            case 'X' -> Pawn_Capture(Col, T, Start, Help);
            case 'W' -> Rook(Col, T, Start, Help);
            case 'S' -> Knight(Col, T, Start, Help);
            case 'G' -> Bishop(Col, T,Start, Help);
            case 'H' -> Queen(Col, T, Start, Help);
            case 'K' -> King(Col, T, Start , Help);
            default -> System.out.print("Choose_fig fault");
        }
        if(Result.IsEmpty()){
            System.out.print("Position not found");
        }
    }

    void Pawn_Capture(boolean Col, Bufor T, Position Start,Position Help) {
        //Manualnie
        if (Col) {
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
            Result=Check.Check_Line(Start,new Position(0,-1),11,T);
        }
        else {
            //czarne
            Result=Check.Check_Line(Start,new Position(0,1),21,T);
        }
    }

    void Rook(boolean Col, Bufor T, Position Start,Position Help) {
    if(Help.IsEmpty()){
        Position[] Steps= {new Position(-1,0),new Position(1,0),new Position(0,1),new Position(0,-1)};
        Position Temp=new Position((Start));
            int i=0;
            while(Result.IsEmpty()) {
                if (Col) Check.Check_Line(Temp, Steps[i], 12, T);
                else Check.Check_Line(Temp, Steps[i], 22, T);
                i++;
                if (i > 3) {
                    System.out.print("Rook while fail");
                    break;
                }
            }
    }
    else{
        if(Help.x!=-1){
        //TODO
        }
        else{
        //TODO
        }
    }

    }

    void Knight(boolean Col, Bufor T, Position Start,Position Help) {
        if(Help.IsEmpty()){
            Position[] Steps={
                    new Position(2,1),
                    new Position(2,-1),
                    new Position(1,2),
                    new Position(1,-2),
                    new Position(-1,2),
                    new Position(-1,-2),
                    new Position(-2,1),
                    new Position(-2,-1),
            };
            for(Position P : Steps){
                P.Add(Start);
                if(Col){
                    if(Check.Check_Position(P,13,T)) {
                        Result = P;
                        break;
                    }
                }
                else {
                    if(Check.Check_Position(P,23,T)) {
                        Result = P;
                        break;
                    }
                }

            }
        }
        else{
            //TODO
        }
    }

    void Bishop(boolean Col, Bufor T, Position Start,Position Help) {
        if(Help.IsEmpty()){
            Position[] Steps= {new Position(-1,-1),new Position(1,1),new Position(-1,1),new Position(1,-1)};
            Position Temp=new Position((Start));
            int i=0;
            while(Result.IsEmpty()) {
                if (Col) Result=Check.Check_Line(Temp, Steps[i], 14, T);
                else Result=Check.Check_Line(Temp, Steps[i], 24, T);
                i++;
                if (i > 3) {
                    System.out.print("Bishop while fail");
                    break;
                }
            }
        }
        else{
            if(Help.x!=-1){
                //TODO
            }
            else{
                //TODO
            }
        }
    }

    void Queen(boolean Col, Bufor T, Position Start,Position Help) {
        if(Help.IsEmpty()){
            Position[] Steps={
                    new Position(1,0),
                    new Position(-1,0),
                    new Position(0,1),
                    new Position(0,-1),
                    new Position(1,1),
                    new Position(1,-1),
                    new Position(-1,1),
                    new Position(-1,-1),
            };
            for(Position P : Steps){
                P.Add(Start);
                if(Check.OnBoard(P)){
                    if(Col)Check.Check_Position(P,15,T);
                    else Check.Check_Position(P,25,T);
                    if(!Result.IsEmpty())break;
                }
            }
        }
        else{
            //TODO
        }
    }

    void King(boolean Col, Bufor T,Position Start,Position Help) {
        Position[] Steps={
                new Position(1,0),
                new Position(-1,0),
                new Position(0,1),
                new Position(0,-1),
                new Position(1,1),
                new Position(1,-1),
                new Position(-1,1),
                new Position(-1,-1),
        };
        for(Position P : Steps){
            P.Add(Start);
            if(Check.OnBoard(P)){
                if(Col)Check.Check_Position(P,16,T);
                else Check.Check_Position(P,26,T);
                if(!Result.IsEmpty())break;
            }
        }
    }
}
