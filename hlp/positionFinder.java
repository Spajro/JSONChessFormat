package hlp;

import dts.Board;
import dts.Position;

import java.util.Dictionary;

public class positionFinder {
    public static Position[] basicSteps= {new Position(-1,0),new Position(1,0),new Position(0,1),new Position(0,-1)};
    public static Position[] knightSteps={
            new Position(2,1),
            new Position(2,-1),
            new Position(1,2),
            new Position(1,-2),
            new Position(-1,2),
            new Position(-1,-2),
            new Position(-2,1),
            new Position(-2,-1),
    };
    public static Position[] diagonalSteps= {new Position(-1,-1),new Position(1,1),new Position(-1,1),new Position(1,-1)};
    public static Position[] fullSteps={
            new Position(1,0),
            new Position(-1,0),
            new Position(0,1),
            new Position(0,-1),
            new Position(1,1),
            new Position(1,-1),
            new Position(-1,1),
            new Position(-1,-1),
    };
    public static Position chooseFig(char C, int  col, Board T, Position start, Position aux) {
        switch (C) {
            case ' ' -> {
                if(!aux.isEmpty()){
                    System.err.print("aux for pawn");
                }
                return pawn(col, T, start);
            }
            case 'X' -> {
                return pawnCapture(col, T, start, aux);
            }
            case 'W' -> {
                return rook(col, T, start, aux);
            }
            case 'S' -> {
                return knight(col, T, start, aux);
            }
            case 'G' -> {
                return bishop(col, T,start, aux);
            }
            case 'H' -> {
                return queen(col, T, start, aux);
            }
            case 'K' -> {
                return king(col, T, start , aux);
            }
            default -> System.out.print("Choose_fig fault");
        }
        return new Position();
    }

    private static Position pawnCapture(int col, Board T, Position start, Position aux) {
        //Manualnie
        Position result= new Position(start);
        if (Board.isWhite(col)) {
            start.add(0,-1);
            if (aux.getX() == -1) {
                if (start.getX() == 1) {
                    result.add(1,0);
                }
                else if (start.getX() == 8) {
                    result.add(-1,0);
                }
                else {
                    if (T.checkPiece(Board.WPAWN,start.getX() + 1, start.getY())) {
                        result.add(1,0);
                    } else if (T.checkPiece(Board.WPAWN,start.getX() - 1, start.getY())) {
                        result.add(-1,0);
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            }
            else {
                result= new Position(aux.getX(),start.getY());
            }
        } else {
            start.add(0,1);
            if (aux.getX() == -1) {
                if (start.getX() == 1) {
                    result.add(1,0);
                }
                else if (start.getX()== 8) {
                    result.add(-1,0);
                }
                else {
                    if(T.checkPiece(Board.BPAWN,start.getX() + 1, start.getY())) {
                        result.add(1,0);
                    } else if (T.checkPiece(Board.BPAWN,start.getX() - 1, start.getY())){
                        result.add(-1,0);
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            }
            else {
                result= new Position(aux.getX(),start.getY());
            }
        }
        return result;
    }

    private static Position pawn(int col, Board T, Position start) {
        if (Board.isWhite(col)) {
            //biale
            return checkLine(start,new Position(0,-1),Board.WPAWN,T);
        }
        else {
            //czarne
            return checkLine(start,new Position(0,1),Board.BPAWN,T);
        }
    }

    private static Position rook(int col, Board T, Position start, Position aux) {
        Position result=new Position();
        if(aux.isEmpty()){
            Position temp=new Position((start));
                int i=0;
                while(result.isEmpty()) {
                    if (Board.isWhite(col)) checkLine(temp, basicSteps[i], Board.WROOK, T);
                    else checkLine(temp, basicSteps[i], Board.BROOK, T);
                    i++;
                    if (i > 3) {
                        System.out.print("Rook while fail");
                        break;
                    }
                }
        }
        else{
            if(aux.getX()!=-1){
            //TODO
            }
            else{
            //TODO
            }
        }
        return result;
    }

    private static Position knight(int col, Board T, Position start, Position aux) {
        if(aux.isEmpty()){
            for(Position p : knightSteps){
                p.add(start);
                if(Board.isWhite(col)){
                    if(T.checkPiece(Board.WKNIGHT,p)) {
                        return p;
                    }
                }
                else {
                    if(T.checkPiece(Board.BKNIGHT,p)) {
                        return p;
                    }
                }
            }
        }
        else{
            //TODO
        }
        return new Position();
    }

    private static Position bishop(int col, Board T, Position start, Position aux) {
        Position result=new Position();
        if(aux.isEmpty()){
            Position temp=new Position((start));
            int i=0;
            while(result.isEmpty()) {
                if(Board.isWhite(col)) result=checkLine(temp, diagonalSteps[i], Board.WBISHOP, T);
                else result=checkLine(temp, diagonalSteps[i], Board.BBISHOP, T);
                i++;
                if (i > 3) {
                    System.out.print("Bishop while fail");
                    break;
                }
            }
        }
        else{
            if(aux.getX()!=-1){
                //TODO
            }
            else{
                //TODO
            }
        }
        return result;
    }

    private static Position queen(int col, Board T, Position start, Position aux) {
        if(aux.isEmpty()){
            for(Position p : fullSteps){
                p.add(start);
                if(p.isOnBoard()){
                    if(Board.isWhite(col))if(T.checkPiece(Board.WQUEEN,p))return p;
                    else if(T.checkPiece(Board.BQUEEN,p))return p;
                }
            }
        }
        else{
            //TODO
        }
        return new Position();
    }

    private static Position king(int col, Board T, Position start, Position aux) {
        for(Position p: fullSteps){
            p.add(start);
            if(p.isOnBoard()){
                if(Board.isWhite(col))if(T.checkPiece(Board.WKING,p))return p;
                else if(T.checkPiece(Board.BKING,p))return p;
            }
        }
        return new Position();
    }
    
    public static Position checkLine(Position start, Position step, int figId,Board T){
        Position temp=new Position(start);
        while(temp.isOnBoard()){
            if(T.checkPiece(figId,temp)){
                return temp;
            }
            else{
                if(!T.checkPiece(0,temp)){
                    //jesli na linii jest inna figura
                    break;
                }
                temp.add(step);
            }
        }
        return new Position(-1,-1);
    }
}