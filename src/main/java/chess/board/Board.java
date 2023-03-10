package chess.board;

import chess.Position;

import java.util.Arrays;

public class Board {
    public static final int SIZE = 8;
    public static final byte EMPTY = 0;
    public static final byte WPAWN = 1;
    public static final byte WKNIGHT = 4;
    public static final byte WBISHOP = 7;
    public static final byte WROOK = 10;
    public static final byte WQUEEN = 13;
    public static final byte WKING = 16;
    public static final byte BPAWN = 2;
    public static final byte BKNIGHT = 5;
    public static final byte BBISHOP = 8;
    public static final byte BROOK = 11;
    public static final byte BQUEEN = 14;
    public static final byte BKING = 17;
    private final byte[][] T;

    private Board() {
        T = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                set(EMPTY, i, j);
            }
        }
    }

    private Board(Board B) {
        T = new byte[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                set(B.get(i, j), i, j);
            }
        }
    }

    public void write(byte id, Position position) {
        set(id, position.getX() - 1, position.getY() - 1);
    }

    public byte read(Position position) {
        return get(position.getX() - 1, position.getY() - 1);
    }

    private void set(byte id, int x, int y) {
        T[x][y] = id;
    }

    private byte get(int x, int y) {
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
