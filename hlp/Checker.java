package hlp;

import dts.Bufor;

public class Checker {
    Bufor T;
    Checker(Bufor Bf){
        T=Bf;
    }
    boolean Check_Position(int tx,int ty,int FigId){
        if(T.get(tx,ty)==FigId){
            return true;
        }
        else return false;
    }
    boolean OutOfMap(int x,int y){
        if(x<-1 || y<-1 || x>7 || y>7){
            return false;
        }
        else return true;
    }
    boolean Check_Line(int tx,int ty,int StepX,int StepY,int FigId){
        while(!OutOfMap(tx,ty)){
            if(Check_Position(tx,ty,FigId)){
                return true;
            }
            else{
                tx+=StepX;
                ty+=StepY;
            }
        }
        return false;
    }
}
