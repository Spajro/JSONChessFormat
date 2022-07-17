package src.data.dts;

import src.data.dts.color.Color;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    //Class representing chess board
    //statics int%3==1 is white, 2 is black
    public static final int SIZE = 8;
    public static final int EMPTY = 0;
    public static final int WPAWN = 1;
    public static final int WKNIGHT = 4;
    public static final int WBISHOP = 7;
    public static final int WROOK = 10;
    public static final int WQUEEN = 13;
    public static final int WKING = 16;
    public static final int BPAWN = 2;
    public static final int BKNIGHT = 5;
    public static final int BBISHOP = 8;
    public static final int BROOK = 11;
    public static final int BQUEEN = 14;
    public static final int BKING = 17;
    //colors
    private final int[][] T;

    private Board() {
        T = new int[SIZE][SIZE];
        for (int[] i : T) {
            for (int j : i) {
                j = EMPTY;
            }
        }
    }

    private Board(Board B) {
        T = new int[SIZE][SIZE];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                set(B.get(i, j), i, j);
            }
        }
    }

    public void write(int val, Position position) {
        set(val, position.getX() - 1, position.getY() - 1);
    }

    public int read(Position position) {
        return get(position.getX() - 1, position.getY() - 1);
    }

    private void set(int val, int x, int y) {
        T[x][y] = val;
    }

    private int get(int x, int y) {
        return T[x][y];
    }

    public static Board getBlank() {
        return new Board();
    }

    public static Board getStart() {
        Board b = new Board();
        for (int i = 0; i <= 7; i++) {
            b.set(WPAWN, i, 1);
            b.set(BPAWN, i, 6);
        }
        b.set(WROOK, 0, 0);
        b.set(WROOK, 7, 0);
        b.set(WKNIGHT, 1, 0);
        b.set(WKNIGHT, 6, 0);
        b.set(WBISHOP, 2, 0);
        b.set(WBISHOP, 5, 0);
        b.set(WQUEEN, 3, 0);
        b.set(WKING, 4, 0);
        b.set(BROOK, 0, 7);
        b.set(BROOK, 7, 7);
        b.set(BKNIGHT, 1, 7);
        b.set(BKNIGHT, 6, 7);
        b.set(BBISHOP, 2, 7);
        b.set(BBISHOP, 5, 7);
        b.set(BQUEEN, 3, 7);
        b.set(BKING, 4, 7);
        return b;
    }

    public static Board getCopy(Board b) {
        return new Board(b);
    }

    public boolean checkPiece(int figureId, int x, int y) {
        return get(x, y) == figureId;
    }

    public boolean checkPiece(int figureId, Position temp) {
        return checkPiece(figureId, temp.getX(), temp.getY());
    }

    public static Color getPieceColor(int figureId){
        if(figureId%3==1){
            return Color.white;
        } else if (figureId % 3 == 2) {
            return Color.black;
        }
        else throw new IllegalArgumentException("Wrong figureId");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(T, board.T);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(T);
    }
}
