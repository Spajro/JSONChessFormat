package hlp;

import dts.Board;
import dts.Move;
import dts.Position;

public class Translator {

    public static Move Algebraic_to_move(Board t, String moveName, int color) {
        Move Result= new Move();
        Result.SetName(moveName);
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
        switch (moveName.length()) {
            case 2: //ruch pionka do przodu
                tx = moveName.charAt(0);
                ty = moveName.charAt(1);
                f=' ';
                break;
            case 3: //ruch figury lub bicie pionem lub roszada krotka
                if (moveName.equals("O-O")) {
                    //roszada krotka
                    Rosz = true;
                    if (Board.isWhite(color)) {
                        R_type = 1;
                    } else {
                        R_type = 3;
                    }
                } else if (moveName.charAt(0) == 'x') {
                    //bicie pionkiem
                    tx = moveName.charAt(1);
                    ty = moveName.charAt(2);
                    f='X';
                } else {
                    //ruch figury
                    f = moveName.charAt(0);
                    tx = moveName.charAt(1);
                    ty = moveName.charAt(2);
                }
                break;
            case 4: //ruch jednej z mozliwych figur lub bicie figurą lub jednym z mozliwych pionkow
                if (moveName.charAt(1) == 'x') {
                    //bicie figura
                    f = moveName.charAt(0);
                    tx = moveName.charAt(2);
                    ty = moveName.charAt(3);
                } else if (moveName.charAt(0) == 'x') {
                    //bicie jednym z mozliwych pionkow
                    thx = moveName.charAt(1);
                    tx = moveName.charAt(2);
                    ty = moveName.charAt(3);
                    f='X';
                } else {
                    //ruch jednej z mozliwych figur
                    f = moveName.charAt(0);
                    thx = moveName.charAt(1);
                    tx = moveName.charAt(2);
                    ty = moveName.charAt(3);
                }
                break;
            case 5: //bicie jedną z mozliwych figur lub roszada dluga
                if (moveName.equals("O-O-O")) {
                    //roszada długa
                    Rosz = true;
                    if (Board.isWhite(color)) {
                        R_type = 2;
                    } else {
                        R_type = 4;
                    }
                } else {
                    //bicie jedną z mozliwych figur
                    f = moveName.charAt(0);
                    thx = moveName.charAt(2);
                    tx = moveName.charAt(3);
                    ty = moveName.charAt(4);
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
            Position temp=positionFinder.chooseFig(f,color,t,new Position(x,y),new Position(hx,hy));
            Result.Update(temp.getX(),temp.getY(),x,y);
        }
        return Result;
    }

    public static int Column_to_num(char column){
        switch (column){
            case 'a' -> {
                return 1;
            }
            case 'b' -> {
                return 2;
            }
            case 'c' -> {
                return 3;
            }
            case 'd' -> {
                return 4;
            }
            case 'e' -> {
                return 5;
            }
            case 'f' -> {
                return 6;
            }
            case 'g' -> {
                return 7;
            }
            case 'h' -> {
                return 8;
            }
            default -> System.out.print("Column_to_num fault");
        }
        return -1;
    }

    public static String Num_to_Fig(int F){
        switch(F){
            case Board.WPAWN -> {
                return "BP";
            }
            case Board.WROOK -> {
                return "BW";
            }
            case Board.WKNIGHT -> {
                return "BS";
            }
            case Board.WBISHOP -> {
                return "BG";
            }
            case Board.WQUEEN -> {
                return "BH";
            }
            case Board.WKING -> {
                return "BK";
            }
            case Board.BPAWN -> {
                return "CP";
            }
            case Board.BROOK -> {
                return "CW";
            }
            case Board.BKNIGHT -> {
                return "CS";
            }
            case Board.BBISHOP -> {
                return "CG";
            }
            case Board.BQUEEN -> {
                return "CH";
            }
            case Board.BKING -> {
                return "CK";
            }
            default -> {
                System.out.print("Num_to_Fig fault");
                return null;
            }

        }
    }

}
