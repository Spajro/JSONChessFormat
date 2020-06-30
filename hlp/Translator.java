package hlp;

import dts.Bufor;
import dts.Move;

public class Translator {
    Position_finder Pf;
    Translator(){
        Pf=new Position_finder();
    }
    public Move Algebraic_to_move(Bufor T, String M, boolean C) {
        Move Result= new Move();
        char y=' ';
        int x=-1;
        char f=' ';
        switch (M.length()) {
            case 2:
                //ruch pionka do przodu
                y=M.charAt(0);
                x=M.charAt(1);
                    Pf.Choose_fig(' ',C,T,x,y,-1);
                    Result.Update(x,y,Pf.x,Pf.y);
                break;


            case 3:
                //ruch figury lub bicie pionem lub roszada krotka
                if(M.equals("O-O")){
                    //roszada
                    if(C){
                        Result.Roszada(1);
                    }
                    else{
                        Result.Roszada(3);
                    }
                }
                else if(M.charAt(0)=='x'){
                    //bicie pionkiem
                     y=M.charAt(1);
                     x=M.charAt(2);
                    Pf.Choose_fig(' ',C,T,x,y,-1);
                    Result.Update(x,y,Pf.x,Pf.y);
                }
                else{
                    //ruch figury
                    y=M.charAt(1);
                    x=M.charAt(2);
                    f=M.charAt(0);
                    Pf.Choose_fig(f,C,T,x,y,-1);
                    Result.Update(x,y,Pf.x,Pf.y);
                }
                break;


            case 4:
                //ruch jednej z mozliwych figur lub bicie figurą lub jednym z mozliwych pionkow
                if(M.charAt(1)=='x'){
                    //bicie figura
                }
                else if(M.charAt(0)=='x'){
                    //bicie jednym z mozliwych pionkow
                }
                else{
                    //ruch jednej z mozliwych figur
                }
                break;
            case 5:
                //bicie jedną z mozliwych figur lub roszada dluga
                if(M.equals("O-O-O")){
                    //roszada długa
                }
                else{
                    //bicie jedną z mozliwych figur
                }
                break;


            default:
                System.out.print("Unable to translate");
                break;

        }
        return Result;
    }
    public int Column_to_num(char C){
        //TODO
        return 0;
    }
    public String Num_to_Fig(int F){
        //TODO
        return null;
    }

}
