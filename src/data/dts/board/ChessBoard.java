package src.data.dts.board;

import src.data.dts.Move;
import src.data.dts.Position;
import src.data.dts.color.Color;

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

    public int read(Position position) {
        return board.read(position);
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
}
