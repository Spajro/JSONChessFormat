package prg;

import dts.Bufor;
import dts.Move;

public class Translator {
    public Move Algebraic_to_move(Bufor T, String M, boolean C) {
        //TODO
        Move Result= new Move();
        switch (M.length()) {
            case 2:
                //ruch pionka do przodu
                char y=M.charAt(0);
                int x=M.charAt(1);
                if(C){
                    //bialy pionek


                }
                else{
                    //czarny pionek
                }

                break;


            case 3:
                //ruch figury lub bicie pionem lub roszada krotka
                if(M.equals("O-O")){
                    //roszada
                }
                else if(M.charAt(0)=='x'){
                    //bicie pionkiem
                }
                else{
                    //ruch figury
                }
                break;


            case 4:
                //ruch jednej z mozliwych figur lub bicie figurą
                if(M.charAt(1)=='x'){
                    //bicie figura
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
                System.out.print("Unable to unify");
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
