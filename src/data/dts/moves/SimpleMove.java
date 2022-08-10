package data.dts.moves;

import data.dts.Position;
import data.dts.board.Board;

public class SimpleMove extends RawMove implements ValidMove{
    private final Board board;
    public SimpleMove(Position oldPosition, Position newPosition,Board board) {
        super(oldPosition, newPosition);
        this.board=board;
    }

    public SimpleMove(RawMove move,Board board){
        super(move.getStartPosition(),move.getEndPosition());
        this.board=board;
    }

    @Override
    public Board makeMove() {
        Board newBoard=Board.getCopy(board);
        newBoard.write(board.read(getStartPosition()), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        return newBoard;
    }
}
