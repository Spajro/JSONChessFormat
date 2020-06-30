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
        boolean Rosz=false;
        boolean Fault=false;
        int R_type=-1;
        int y=-1;
        char tx=' ';
        char ty=' ';
        int x=-1;
        char f=' ';
        char thx=' ';
        int hx=-1;
        switch (M.length()) {
            case 2:
                //ruch pionka do przodu
                tx=M.charAt(0);
                ty=M.charAt(1);
                break;


            case 3:
                //ruch figury lub bicie pionem lub roszada krotka
                if(M.equals("O-O")){
                    //roszada krotka
                    Rosz=true;
                    if(C){
                        R_type=1;
                    }
                    else{
                        R_type=3;
                    }
                }
                else if(M.charAt(0)=='x'){
                    //bicie pionkiem
                     tx=M.charAt(1);
                     ty=M.charAt(2);
                }
                else{
                    //ruch figury
                    f=M.charAt(0);
                    tx=M.charAt(1);
                    ty=M.charAt(2);
                }
                break;


            case 4:
                //ruch jednej z mozliwych figur lub bicie figurą lub jednym z mozliwych pionkow
                if(M.charAt(1)=='x'){
                    //bicie figura
                    f=M.charAt(0);
                    tx=M.charAt(2);
                    ty=M.charAt(3);
                }
                else if(M.charAt(0)=='x'){
                    //bicie jednym z mozliwych pionkow
                    thx=M.charAt(1);
                    tx=M.charAt(2);
                    ty=M.charAt(3);
                }
                else{
                    //ruch jednej z mozliwych figur
                    f=M.charAt(0);
                    thx=M.charAt(1);
                    tx=M.charAt(2);
                    ty=M.charAt(3);
                }
                break;
            case 5:
                //bicie jedną z mozliwych figur lub roszada dluga
                if(M.equals("O-O-O")){
                    //roszada długa
                    Rosz=true;
                    if(C){
                        R_type=2;
                    }
                    else{
                        R_type=4;
                    }
                }
                else{
                    //bicie jedną z mozliwych figur
                    f=M.charAt(0);
                    thx=M.charAt(2);
                    tx=M.charAt(3);
                    ty=M.charAt(4);
                }
                break;


            default:
                Fault=true;
                System.out.print("Unable to translate");
                break;

        }
        if(Fault){
            return null;
        }
        else if(Rosz){
            Result.Roszada(R_type);
        }
        else{
            x=Column_to_num(tx);
            y=Character.getNumericValue(ty);
            if(thx!=' '){
                hx=Column_to_num(thx);
            }
            Pf.Choose_fig(f,C,T,x,y,hx);
            Result.Update(x,y,Pf.x,Pf.y);
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
