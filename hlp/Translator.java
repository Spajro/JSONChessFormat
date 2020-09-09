package hlp;

import dts.Bufor;
import dts.Move;

public class Translator {
    Position_finder Pf;
    public Translator(){
        Pf=new Position_finder();
    }

    public Move Algebraic_to_move(Bufor T, String M, boolean C) {
        Move Result= new Move();
        boolean Rosz=false;
        boolean Fault=false;
        int R_type=-1;
        int y;
        char tx=0;
        char ty=0;
        int x;
        char f = 0;
        char thx = 0;
        int hx=-1;
        int hy=-1;
        switch (M.length()) {
            case 2: //ruch pionka do przodu
                tx = M.charAt(0);
                ty = M.charAt(1);
                f=' ';
                break;
            case 3: //ruch figury lub bicie pionem lub roszada krotka
                if (M.equals("O-O")) {
                    //roszada krotka
                    Rosz = true;
                    if (C) {
                        R_type = 1;
                    } else {
                        R_type = 3;
                    }
                } else if (M.charAt(0) == 'x') {
                    //bicie pionkiem
                    tx = M.charAt(1);
                    ty = M.charAt(2);
                    f='X';
                } else {
                    //ruch figury
                    f = M.charAt(0);
                    tx = M.charAt(1);
                    ty = M.charAt(2);
                }
                break;
            case 4: //ruch jednej z mozliwych figur lub bicie figurą lub jednym z mozliwych pionkow
                if (M.charAt(1) == 'x') {
                    //bicie figura
                    f = M.charAt(0);
                    tx = M.charAt(2);
                    ty = M.charAt(3);
                } else if (M.charAt(0) == 'x') {
                    //bicie jednym z mozliwych pionkow
                    thx = M.charAt(1);
                    tx = M.charAt(2);
                    ty = M.charAt(3);
                    f='X';
                } else {
                    //ruch jednej z mozliwych figur
                    f = M.charAt(0);
                    thx = M.charAt(1);
                    tx = M.charAt(2);
                    ty = M.charAt(3);
                }
                break;
            case 5: //bicie jedną z mozliwych figur lub roszada dluga
                if (M.equals("O-O-O")) {
                    //roszada długa
                    Rosz = true;
                    if (C) {
                        R_type = 2;
                    } else {
                        R_type = 4;
                    }
                } else {
                    //bicie jedną z mozliwych figur
                    f = M.charAt(0);
                    thx = M.charAt(2);
                    tx = M.charAt(3);
                    ty = M.charAt(4);
                }
                break;
            default:
                Fault = true;
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
            y=Character.getNumericValue(ty)-1;
            if(thx!=0){
                hx=Column_to_num(thx);
            }
            Pf.Choose_fig(f,C,T,new Position(x,y),new Position(hx,hy));
            Result.Update(Pf.Result.x,Pf.Result.y,x,y);
        }
        Pf.Clean();
        return Result;
    }

    public int Column_to_num(char C){
        switch (C){
            case 'a' -> {
                return 0;
            }
            case 'b' -> {
                return 1;
            }
            case 'c' -> {
                return 2;
            }
            case 'd' -> {
                return 3;
            }
            case 'e' -> {
                return 4;
            }
            case 'f' -> {
                return 5;
            }
            case 'g' -> {
                return 6;
            }
            case 'h' -> {
                return 7;
            }
            default -> System.out.print("Column_to_num fault");
        }
        return -1;
    }

    public String Num_to_Fig(int F){
        switch(F){
            case 11 -> {
                return "BP";
            }
            case 12 -> {
                return "BW";
            }
            case 13 -> {
                return "BS";
            }
            case 14 -> {
                return "BG";
            }
            case 15 -> {
                return "BH";
            }
            case 16 -> {
                return "BK";
            }
            case 21 -> {
                return "CP";
            }
            case 22 -> {
                return "CW";
            }
            case 23 -> {
                return "CS";
            }
            case 24 -> {
                return "CG";
            }
            case 25 -> {
                return "CH";
            }
            case 26 -> {
                return "CK";
            }
            default -> {
                System.out.print("Num_to_Fig fault");
                return null;
            }

        }
    }

}
