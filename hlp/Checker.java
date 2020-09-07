package hlp;

import dts.Bufor;

public class Checker {

    boolean Check_Position(Position P,int FigId,Bufor T){
        return T.get(P.x,P.y) == FigId;
    }
    boolean OutOfMap(Position P){
        return P.x >= -1 && P.y >= -1 && P.x <= 7 && P.y <= 7;
    }
    Position Check_Line(Position Temp,Position Step,int FigId,Bufor T){
        while(!OutOfMap(Temp)){
            if(Check_Position(Temp,FigId,T)){
                return Temp;
            }
            else{
                Temp.Add(Step);
            }
        }
        return new Position(-1,-1);
    }
}
