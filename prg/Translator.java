package prg;

import dts.Bufor;
import dts.Move;

public class Translator {
    public Move Unify_to_move(Bufor T, String M, boolean C) {
        //TODO
        Move Result= new Move();
        switch (M.length()) {
            case 2:
                //ruch pionka
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
                //ruch figury
                break;


            case 4:
                //ruch jednej z mozliwych figur
                break;


            default:
                System.out.print("Unable to unify");
                break;

        }
        return null;
    }
    public int Column_to_num(char C){
        //TODO
    }
    public String Num_to_Fig(int F){
        //TODO
    }

}
