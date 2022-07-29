package src.data.dts.board;

import src.data.dts.Move;
import src.data.dts.Position;
import src.data.dts.color.Color;
import src.data.dts.fields.Field;

public class ChessBoard {
    private final Board board;
    private final Color color;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
    }

    private ChessBoard(Board board, Color color) {
        this.board = board;
        this.color = color;
    }

    @Deprecated
    public int read(Position position) {
        return board.read(position);
    }

    public Field getField(Position position){
        //TODO
        return null;
    }

    public ChessBoard makeMove(Move move) {
        if (isLegal(move)) {
            Board tempBoard = Board.getCopy(board);
            move.makeMove(tempBoard);
            return new ChessBoard(tempBoard, color.swap());
        }
        return this;
    }

    private boolean isLegal(Move move) {
        return Board.getPieceColor(board.read(move.getOldPosition())).equal(color) && move.isCorrect();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoard that = (ChessBoard) o;

        if (!board.equals(that.board)) return false;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        int result = board.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }
}
