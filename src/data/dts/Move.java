package data.dts;

import data.dts.board.Board;

import java.io.Serializable;

public class Move implements Serializable {
    private String name;
    private Position oldPosition;
    private Position newPosition;
    private int castle;
    public static final int NO_CASTLE = 0;
    public static final int WHITE_SHORT_CASTLE = 1;
    public static final int WHITE_LONG_CASTLE = 2;
    public static final int BLACK_SHORT_CASTLE = 3;
    public static final int BLACK_LONG_CASTLE = 4;

    public Move() {
        oldPosition = new Position();
        newPosition = new Position();
        castle = -1;
    }

    public Move(Position oldPos, Position newPos) {
        oldPosition = oldPos;
        newPosition = newPos;
        castle = 0;
    }

    public Move(int castle) {
        this.castle = castle;
    }

    public void setCastle(int castle) {
        this.castle = castle;
    }

    public void makeMove(Board board) {
        if (castle == 0) {
            board.write(board.read(oldPosition), newPosition);
            board.write(0, oldPosition);
        } else {
            switch (castle) {
                case WHITE_SHORT_CASTLE -> makeShortCastle(board, 1);
                case BLACK_SHORT_CASTLE -> makeShortCastle(board, 8);
                case WHITE_LONG_CASTLE -> makeLongCastle(board, 1);
                case BLACK_LONG_CASTLE -> makeLongCastle(board, 8);
            }
        }
    }

    public boolean isCorrect() {
        return !oldPosition.isEmpty() && !newPosition.isEmpty();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCastle() {
        return castle;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    private void makeShortCastle(Board board, int row) {
        new Move(new Position(5, row), new Position(7, row)).makeMove(board);
        new Move(new Position(8, row), new Position(6, row)).makeMove(board);
    }

    private void makeLongCastle(Board board, int row) {
        new Move(new Position(5, row), new Position(3, row)).makeMove(board);
        new Move(new Position(1, row), new Position(4, row)).makeMove(board);
    }

    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                ", oldPosition=" + oldPosition +
                ", newPosition=" + newPosition +
                ", castle=" + castle +
                '}';
    }
}
