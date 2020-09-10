package hlp;

import dts.Bufor;

public class Checker {

    boolean Check_Position(Position P,int FigId,Bufor T){
        return T.get(P.x,P.y) == FigId;
    }
    boolean OnBoard(Position P){
        return !(P.x < 0 || P.y < 0 || P.x > 7 || P.y > 7);
    }
    Position Check_Line(Position Start,Position Step,int FigId,Bufor T){
        Position Temp=new Position(Start);
        while(OnBoard(Temp)){
            if(Check_Position(Temp,FigId,T)){
                return Temp;
            }
            else{
                if(!Check_Position(Temp,0,T)){
                    //jesli na linii jest inna figura
                    break;
                }
                Temp.Add(Step);
            }
        }
        return new Position(-1,-1);
    }
}
