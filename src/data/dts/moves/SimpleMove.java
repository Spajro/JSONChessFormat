package data.dts.moves;

import data.dts.Position;
import data.dts.board.Board;

public class SimpleMove extends RawMove implements ValidMove{
    private final Board board;
    public SimpleMove(Position oldPosition, Position newPosition,Board board) {
        super(oldPosition, newPosition);
        this.board=board;
    }

    @Override
    public Board makeMove() {
        Board newBoard=Board.getCopy(board);
        newBoard.write(board.read(getOldPosition()), getNewPosition());
        newBoard.write(Board.EMPTY, getOldPosition());
        return newBoard;
    }
}
